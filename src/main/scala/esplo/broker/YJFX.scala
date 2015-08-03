package esplo.broker

import java.util.Calendar

import esplo.currency.Currency.JPY
import esplo.currency.{Price, CurrencyFormatter, SwapInfo}
import org.openqa.selenium.By
import org.openqa.selenium.phantomjs.PhantomJSDriver
import collection.JavaConversions._


// YJFX displays daily swaps in JPY
// pair, sp num, buy, sell
class YJFX extends Broker("YJFX", "http://www.yjfx.jp/gaikaex/mark/swap/today.php") {

  def parse(driver: PhantomJSDriver): List[SwapInfo] = {
    driver.get(url)
    logger.info(s"start parsing $name")

    val today = Calendar.getInstance()

    val table = driver.findElementsByXPath( """//table[@class="tbl"]/tbody/tr""")

    table.toList.flatMap(tr => {
      val pair = tr.findElement(By.className("lll")).getText.trim
      val num = tr.findElement(By.className("date")).getText.trim.toInt
      val buy = tr.findElement(By.className("buy")).getText.trim.toInt
      val sell = tr.findElement(By.className("sell")).getText.trim.toInt

      CurrencyFormatter.str2CurrencyPair(pair, "/") match {
        case Some(p) =>
          Some(new SwapInfo(
            p,
            today,
            Some(num),
            new Price(JPY, buy),
            new Price(JPY, sell)
          ))
        case None =>
          None
      }
    })
  }
}
