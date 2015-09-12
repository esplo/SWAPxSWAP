package esplo

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import com.typesafe.scalalogging.Logger
import esplo.config.Settings
import esplo.currency.{SwapInfo, SwapInfoForDB}
import org.slf4j.LoggerFactory


class DBManager {
  val logger = Logger(LoggerFactory.getLogger("swap-swap:db"))
  logger.info("Start initializing DB")

  val db = {
    // use mongoHost & mongoPort if mongoURI is not set
    if(Settings.appConfig.mongoURI.isEmpty) {
      val client = MongoClient(
        Settings.appConfig.mongoHost,
        Settings.appConfig.mongoPort
      )
      client(Settings.appConfig.mongoDBName)
    }
    else {
      val uri = MongoClientURI(Settings.appConfig.mongoURI)
      MongoClient(uri)(uri.database.get)
    }
  }
  val coll = {
    if (!db.collectionExists(Settings.appConfig.mongoCollectionName))
      db.createCollection(Settings.appConfig.mongoCollectionName, DBObject())
    db(Settings.appConfig.mongoCollectionName)
  }

  logger.info(s"${Settings.appConfig.mongoHost}:${Settings.appConfig.mongoPort}")
  logger.info(s"db: ${Settings.appConfig.mongoDBName}")
  logger.info(s"db: ${Settings.appConfig.mongoCollectionName}")


  // write SwapInfo to DB
  def writeSwapInfo(swaps: List[SwapInfo]) = {

    def insert(obj: SwapInfo) = {
      logger.info(s"insert: $obj")
      coll.insert(grater[SwapInfoForDB].asDBObject(obj.serialize))
    }

    // duplication check
    def isNotDuplicate(swapInfo: SwapInfo): Boolean = {
      val condition = MongoDBObject(
        "broker" -> swapInfo.broker,
        "pair" -> swapInfo.pair.toString,
        "date" -> swapInfo.getDateForMongo
      )
      coll.findOne(condition).isEmpty
    }

    swaps.filter(isNotDuplicate).foreach(insert)
  }
}
