package esplo.broker

import java.time.LocalDate

import esplo.currency.Currency.JPY
import esplo.currency.{CurrencyFormatter, Price, SwapInfo}
import org.openqa.selenium.By
import org.openqa.selenium.phantomjs.PhantomJSDriver

import scala.collection.JavaConversions._


// YJFX displays daily swaps in JPY
// pair, sp num, buy, sell
class YJFX extends Broker("YJFX", "http://www.yjfx.jp/gaikaex/mark/swap/today.php") {

  def parse(driver: PhantomJSDriver): List[SwapInfo] = {
    driver.get(url)
    logger.info(s"start parsing $name")

    val table = driver.findElementsByXPath( """//table[@class="tbl"]/tbody/tr""")

    table.toList.flatMap(tr => {
      val pair = tr.findElement(By.className("lll")).getText.trim
      val num = tr.findElement(By.className("date")).getText.trim.toInt
      val buy = tr.findElement(By.className("buy")).getText.trim.toInt
      val sell = tr.findElement(By.className("sell")).getText.trim.toInt

      CurrencyFormatter.str2CurrencyPair(pair, "/") match {
        case Some(p) =>
          Some(SwapInfo(
            name,
            p,
            LocalDate.now(),
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
