package esplo.broker

import com.typesafe.scalalogging.Logger
import esplo.currency.SwapInfo
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.slf4j.LoggerFactory


abstract class Broker(val name: String, val url: String) {
  val logger = Logger(LoggerFactory.getLogger("swap-swap"))

  def parse(driver: PhantomJSDriver): List[SwapInfo]
}
