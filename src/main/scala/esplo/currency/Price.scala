package esplo.currency

class Price(currency: Currency, value: Long) {
  override def toString = s"$value$currency"
}
