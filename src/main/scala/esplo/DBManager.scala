package esplo

import com.mongodb.casbah.Imports._
import com.typesafe.scalalogging.Logger
import esplo.config.Settings
import esplo.currency.SwapInfo
import org.slf4j.LoggerFactory

class DBManager {
  val logger = Logger(LoggerFactory.getLogger("swap-swap:db"))
  logger.info("Start initializing DB")

  val mongoClient = MongoClient(
    Settings.appConfig.mongoHost,
    Settings.appConfig.mongoPort
  )
  val db = mongoClient(Settings.appConfig.mongoDBName)
  val coll = db(Settings.appConfig.mongoCollectionName)

  logger.info(s"${Settings.appConfig.mongoHost}:${Settings.appConfig.mongoPort}")
  logger.info(s"db: ${Settings.appConfig.mongoDBName}")
  logger.info(s"db: ${Settings.appConfig.mongoCollectionName}")


  // write SwapInfo to DB
  def writeSwapInfo(brokerName: String, swaps: List[SwapInfo]) = {
    def swapInfo2MongoDBObject(swapInfo: SwapInfo): MongoDBObject = {
      MongoDBObject(
        "broker" -> brokerName,
        "pair" -> swapInfo.pair.toString,
        "date" -> swapInfo.date.getTime,
        "numberOfDate" -> swapInfo.numberOfDate.getOrElse(-1),
        "buy" -> swapInfo.buy.value,
        "sell" -> swapInfo.sell.value,
        "swapCurrency" -> swapInfo.buy.currency.name
      )
    }
    def insert(obj: MongoDBObject) = {
      logger.info(s"insert: $obj")
      coll.insert(obj)
    }
    swaps.map(swapInfo2MongoDBObject).foreach(insert)
  }
}
