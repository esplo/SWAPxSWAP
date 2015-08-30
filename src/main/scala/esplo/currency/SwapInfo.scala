package esplo.currency

import java.text.DateFormat
import java.util.{Date, Calendar}


case class SwapInfo(
                     brokerName: String,
                     pair: CurrencyPair,
                     date: Calendar,
                     numberOfDays: Option[Int], // None means that there is no information about the number of days
                     buy: Price,
                     sell: Price
                     ) {

  // convert to DB class
  def serialize: SwapInfoForDB = {
    SwapInfoForDB(
      brokerName,
      pair.toString,
      date.getTime,
      numberOfDays,
      buy.value,
      sell.value,
      buy.currency.name
    )
  }

  override def toString = {
    val format2 = DateFormat.getDateInstance
    s"broker: $brokerName $pair ${format2.format(date.getTime)} buy:$buy sell:$sell num:$numberOfDays"
  }
}


// swap-info class to write DB
case class SwapInfoForDB(
                          brokerName: String,
                          pair: String,
                          date: Date,
                          numberOfDays: Option[Int],
                          buy: Long,
                          sell: Long,
                          swapCurrency: String
                          ) {

  // convert to normal class
  def deserialize: SwapInfo = {
    val currency = CurrencyFormatter.str2Currency(swapCurrency).get
    SwapInfo(
      brokerName,
      CurrencyFormatter.str2CurrencyPair(pair).get,
      { val c = Calendar.getInstance(); c.setTime(date); c },
      numberOfDays,
      new Price(currency, buy),
      new Price(currency, sell)
    )
  }
}
