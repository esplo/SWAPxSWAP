package esplo

import com.typesafe.scalalogging.Logger
import esplo.broker.DMM
import org.slf4j.LoggerFactory


object Main {

  val logger = Logger(LoggerFactory.getLogger("swap-swap"))

  def main(args: Array[String]) {
    val driver = (new DriverManager).driver

    logger.info("start collecting")

    val dmm = new DMM
    println( dmm.parse(driver) )

    logger.info("end collecting")

    driver.quit()
  }

}
