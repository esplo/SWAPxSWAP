package esplo

import com.typesafe.scalalogging.Logger
import esplo.broker.{Broker, Livestar, DMM}
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

    logger.info("end collecting")

    driver.quit()
  }

  def write(driver: PhantomJSDriver)(broker: Broker) = {
    dbManager.writeSwapInfo( broker.name, broker.parse(driver) )
  }

}
