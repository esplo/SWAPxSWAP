package esplo.currency

import java.time.{LocalDate, ZoneId}
import java.util.Date

import org.scalatest.FunSuite


class SwapInfoTest extends FunSuite {

  def fixture =
    new {
      val now = LocalDate.now()
      val swapInfo = SwapInfo(
        "testBroker",
        CurrencyFormatter.str2CurrencyPair("USDJPY").get,
        now,
        Some(3),
        new Price(CurrencyFormatter.str2Currency("JPY").get, 5),
        new Price(CurrencyFormatter.str2Currency("JPY").get, -10)
      )
      val swapInfoDB = SwapInfoForDB(
        "testBroker",
        "USDJPY",
        Date.from( now.atStartOfDay(ZoneId.of("UTC")).toInstant ),
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
