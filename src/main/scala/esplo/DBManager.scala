package esplo

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import com.typesafe.scalalogging.Logger
import esplo.config.Settings
import esplo.currency.{SwapInfo, SwapInfoForDB}
import org.slf4j.LoggerFactory


class DBManager(val settings: Settings) {
  val logger = Logger(LoggerFactory.getLogger("swap-swap:db"))
  logger.info("Start initializing DB")

  val db = {
    // use mongoHost & mongoPort if mongoURI is not set
    if(settings.appConfig.mongoURI.isEmpty) {
      val client = MongoClient(
        settings.appConfig.mongoHost,
        settings.appConfig.mongoPort
      )
      client(settings.appConfig.mongoDBName)
    }
    else {
      val uri = MongoClientURI(settings.appConfig.mongoURI)
      MongoClient(uri)(uri.database.get)
    }
  }
  val coll = {
    if (!db.collectionExists(settings.appConfig.mongoCollectionName))
      db.createCollection(settings.appConfig.mongoCollectionName, DBObject())
    db(settings.appConfig.mongoCollectionName)
  }

  logger.info(s"db: ${settings.appConfig.mongoURI}")
  logger.info(s"${settings.appConfig.mongoHost}:${settings.appConfig.mongoPort}")
  logger.info(s"db: ${settings.appConfig.mongoDBName}")
  logger.info(s"db: ${settings.appConfig.mongoCollectionName}")


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
