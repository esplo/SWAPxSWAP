package esplo.broker

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import esplo.currency.{Price, SwapInfo, CurrencyFormatter, Currency}
import Currency.JPY
import org.openqa.selenium.{By, WebDriver, WebElement}
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}

import scala.collection.JavaConversions._


/**
 * HTmLのtableで提供
 * スワップの価値は円表記
 */
class DMM extends Broker("DMM", "http://fx.dmm.com/market/swapcalendar_fx/index1.html") {

  override def parse(driver: PhantomJSDriver): List[SwapInfo] = {
    driver.get(url)
    logger.info(s"start parsing $name")

    def getSwaps(id: Int) = {
      logger.info(s"start to get swap information on id $id")

      // table that contains swap-info
      def getTable(id: Int): WebElement = {
        // switch tab
        driver.findElementById(s"swapbtn$id").click()

        // wait for getting a swap table
        new WebDriverWait(driver, 10).until(
          new ExpectedCondition[WebElement] {
            override def apply(d: WebDriver) = d.findElement(By.id(s"table$id"))
          }
        )
      }

      // from table to elements
      def table2Elements(table: WebElement): List[List[String]] = {
        // テーブルをパースし、文字列のリストにする
        // 付与日数、買、売が"-"になっている場合（休日）は除外
        table.findElements(By.xpath("tbody/tr")).map(
            _.findElements(By.tagName("td")).map(_.getText).toList
          ).toList.
          filter(!_.contains("-"))
      }

      val table = getTable(id)
      val elements = table2Elements(table)
      val thisYear = new SimpleDateFormat("yyyy").format(new Date).toInt
      val dayFormat = new SimpleDateFormat("yyyy/MM/dd")

      val pairs = "通貨ペア" :: table.findElements(By.xpath("thead/tr/th")).drop(2).map(_.getText).toList
      logger.info(s"pairs: $pairs")

      // elementは3個1セット（付与日数、買い、売り）
      (0 until elements.size / 3).flatMap(_i => {
        val i = _i * 3

        // MM/DD形式の日付をYYYY/MM/DDに変換
        def getDay(day: String): Calendar = {
          val date = dayFormat.parse(s"$thisYear/$day")
          val cal = Calendar.getInstance()
          cal.setTime(date)

          // 現在時刻より進んでいる => 12月の情報なら1年戻して調整
          if (Calendar.getInstance().compareTo(cal) < 0)
            cal.add(Calendar.YEAR, -1)
          cal
        }

        def getPairData(pairs: List[String], elements: List[List[String]]): List[(String, Int, Int, Int)] = {
          /*
          以下の形式の表になっているので、通貨ペアごとのデータに直す

           |     |         | ドル/円  | ユーロ/円|
           |     | 付与日数 |    1    |    1    |
           | 7/3 |    買   |    4    |    10   |
           |     |    売   |   -4    |   -10   |
         */
          // Flattens a tuple ((A,B),C) into (A,B,C)
          def flatten3[A, B, C](t: ((A, B), C)) = (t._1._1, t._1._2, t._2)
          // Flattens a tuple ((A,B),C,D) into (A,B,C,D)
          def flatten4[A, B, C, D](t: ((A, B), C, D)) = (t._1._1, t._1._2, t._2, t._3)
          // fix type for Info
          def fixtype(t: (String, String, String, String)) = (t._1, t._2.toInt, t._3.toInt, t._4.toInt)

          // 先頭は付与日数などなので削る
          val tmpInfo = (pairs zip elements(i).drop(1) zip elements(i + 1) zip elements(i + 2)).tail
          tmpInfo.map(flatten3).map(flatten4).map(fixtype)
        }

        // MM/DDを取り出して変換
        val date = getDay(elements(i).head.take(5))

        // returns List[SwapInfo] from elements
        getPairData(pairs, elements).flatMap(elem => {
          val (pair, num, buy, sell) = elem

          // infoをスワップ情報に変換
          CurrencyFormatter.str2CurrencyPair(pair) match {
            case None => None
            case Some(c) =>
              Some(new SwapInfo(c, date, Some(num), new Price(JPY, buy), new Price(JPY, sell)))
          }
        })
      })
    }

    (1 to 5).toList.flatMap(getSwaps)
  }
}
