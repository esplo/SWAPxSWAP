package esplo.currency

import java.text.DateFormat
import java.util.Calendar


class SwapInfo(
                val pair: CurrencyPair,
                val date: Calendar,
                val numberOfDate: Int,
                val buy: Price,
                val sell: Price
                ) {

  override def toString = {
    val format2 = DateFormat.getDateInstance
    s"$pair ${format2.format(date.getTime)} buy:$buy sell:$sell num:$numberOfDate"
  }
}
