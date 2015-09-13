package esplo

import java.io.File

import com.typesafe.scalalogging.Logger
import esplo.broker.{Broker, DMM, Livestar, YJFX}
import esplo.config.Settings
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.slf4j.LoggerFactory


case class Config(
                   configFile: File = {
                     val path = sys.env.getOrElse("SWAPxSWAP_CONF", "./app.conf")
                     new File(path)
                   })


object Main {

  def main(args: Array[String]) {

    val logger = Logger(LoggerFactory.getLogger("swap-swap"))
    logger.info("start constracting Main")

    // parse args
    val settings = {
      val parser = new scopt.OptionParser[Config]("SWAPxSWAP") {
        head("SWAPxSWAP", "0.5")

        opt[File]('f', "config-file") valueName "<file>" action { (x, c) =>
          c.copy(configFile = x)
        } text "config-file path. default value is ./app.conf"

        help("help") text "prints this usage text"
      }

      val config = parser.parse(args, Config())
      if (config.isEmpty)
        System.exit(0)
      new Settings(config.get.configFile)
    }

    val dbManager = new DBManager(settings)
    val driver = new DriverManager(settings.appConfig.phantomJSPath).driver

    logger.info("start collecting")

    // wrapper for brokers
    def write(driver: PhantomJSDriver)(broker: Broker) = dbManager.writeSwapInfo(broker.parse(driver))

    val writer = write(driver) _
    writer(new DMM)
    writer(new Livestar)
    writer(new YJFX)

    logger.info("end collecting")

    driver.quit()
  }
}
