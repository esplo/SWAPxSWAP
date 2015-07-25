package esplo.currency

class Price(val currency: Currency, val value: Long) {
  override def toString = s"$value$currency"
}
