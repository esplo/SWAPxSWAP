package esplo

import esplo.currency.{CurrencyFormatter, Currency}
import Currency.{EUR, JPY, USD}

class CurrencyFormatterTest extends org.scalatest.FunSuite {

  test("str2CurrencyPair can convert a string which contains name candidates (default delimiter)") {
    val currencyPair = CurrencyFormatter.str2CurrencyPair("USD/円")
    assert(currencyPair.get.base == USD)
    assert(currencyPair.get.quote == JPY)
  }

  test("str2CurrencyPair can convert a string which contains name candidates (other delimiter)") {
    val currencyPair = CurrencyFormatter.str2CurrencyPair("ユーロ#ドル", "#")
    assert(currencyPair.get.base == EUR)
    assert(currencyPair.get.quote == USD)
  }

  test("str2CurrencyPair returns None if given string doesn't contain name candidates") {
    val currencyPair = CurrencyFormatter.str2CurrencyPair("USD/XXXX")
    assert(currencyPair.isEmpty)
  }

}
