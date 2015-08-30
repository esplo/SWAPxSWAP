package esplo.currency

import java.util.Calendar

import org.scalatest.FunSuite


class SwapInfoTest extends FunSuite {

  def fixture =
    new {
      val now = Calendar.getInstance()
      val swapInfo = SwapInfo(
        "testBroker",
        CurrencyFormatter.str2CurrencyPair("USD/JPY").get,
        now,
        Some(3),
        new Price(CurrencyFormatter.str2Currency("JPY").get, 5),
        new Price(CurrencyFormatter.str2Currency("JPY").get, -10)
      )
      val swapInfoDB = SwapInfoForDB(
        "testBroker",
        "USD/JPY",
        now.getTime,
        Some(3),
        5,
        -10,
        "JPY"
      )
    }

  test("serialize/deserialize can convert correctly") {
    val f = fixture
    assert(f.swapInfo.serialize == f.swapInfoDB)
    assert(f.swapInfoDB.deserialize == f.swapInfo)
  }

  test("serialize => deserialize can restore the original data") {
    val f = fixture
    assert(f.swapInfo.serialize.deserialize == f.swapInfo)
  }

  test("deserialize => serialize can restore the original data") {
    val f = fixture
    assert(f.swapInfoDB.deserialize.serialize == f.swapInfoDB)
  }

}
