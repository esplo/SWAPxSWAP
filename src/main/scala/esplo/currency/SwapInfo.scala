package esplo.currency

import java.text.DateFormat
import java.util.Calendar


class SwapInfo(
                val pair: CurrencyPair,
                val date: Calendar,
                val numberOfDays: Option[Int],  // None means that there is no information about the number of days
                val buy: Price,
                val sell: Price
                ) {

  override def toString = {
    val format2 = DateFormat.getDateInstance
    s"$pair ${format2.format(date.getTime)} buy:$buy sell:$sell num:${numberOfDays.getOrElse(-1)}"
  }
}
