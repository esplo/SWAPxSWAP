package esplo.currency

import java.text.DateFormat
import java.util.Calendar


case class SwapInfo(
                   brokerName: String,
                   pair: CurrencyPair,
                   date: Calendar,
                   numberOfDays: Option[Int],  // None means that there is no information about the number of days
                   buy: Price,
                   sell: Price
                ) {

  override def toString = {
    val format2 = DateFormat.getDateInstance
    s"$pair ${format2.format(date.getTime)} buy:$buy sell:$sell num:${numberOfDays.getOrElse(-1)}"
  }
}
