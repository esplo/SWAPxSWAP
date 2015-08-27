package esplo.broker

import java.text.SimpleDateFormat
import java.util.Calendar

import esplo.currency.Currency.JPY
import esplo.currency.{CurrencyFormatter, Price, SwapInfo}
import org.openqa.selenium.phantomjs.PhantomJSDriver


// Livestar displays only the information today, and old information is saved as PDF files
class Livestar extends Broker("Livestar", "http://www.live-sec.co.jp/fx/swap/") {

  val idList = List(
    "usdjpy", "eurjpy", "gbpjpy", "audjpy", "nzdjpy", "cadjpy",
    "chfjpy", "audchf", "audnzd", "audusd", "euraud", "eurcad",
    "eurchf", "eurgbp", "eurnzd", "eurusd", "gbpaud", "gbpchf",
    "gbpnzd", "gbpusd", "nzdusd", "usdcad", "usdchf", "zarjpy"
  )

  def parse(driver: PhantomJSDriver): List[SwapInfo] = {
    driver.get(url)
    logger.info(s"start parsing $name")

    val date = {
      val format = new SimpleDateFormat("yyyy年MM月dd日")
      val date = format.parse(driver.findElementById("nowtime_sall").getText)
      val cal = Calendar.getInstance()
      cal.setTime(date)
      cal
    }

    idList.flatMap(id => {
      CurrencyFormatter.str2CurrencyPair(id) match {
        case None => None
        case Some(currencyPairName) =>
          val bidID = s"sallbid_$id"
          val askID = s"sallask_$id"
          Some(new SwapInfo(
            currencyPairName,
            date,
            None,
            new Price(JPY, driver.findElementById(askID).getText.toLong),
            new Price(JPY, driver.findElementById(bidID).getText.toLong)))
      }})
  }
}
