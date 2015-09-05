package esplo

import com.typesafe.scalalogging.Logger
import esplo.broker.{Broker, DMM, Livestar, YJFX}
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.slf4j.LoggerFactory


object Main {

  val logger = Logger(LoggerFactory.getLogger("swap-swap"))
  val dbManager = new DBManager

  def main(args: Array[String]) {
    val driver = (new DriverManager).driver

    logger.info("start collecting")

    val writer = write(driver) _
    writer(new DMM)
    writer(new Livestar)
    writer(new YJFX)

    logger.info("end collecting")

    driver.quit()
  }

  def write(driver: PhantomJSDriver)(broker: Broker) = {
    dbManager.writeSwapInfo(broker.parse(driver))
  }

}
