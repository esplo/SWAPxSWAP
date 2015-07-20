package esplo.currency


object CurrencyFormatter {

  // 通貨ペアを表す文字列を通貨ペアクラスに変換
  def str2CurrencyPair(str: String, delim: String = "/"): Option[CurrencyPair] = {
    val currencies = str.split(delim)

    if (currencies.length != 2)
      None
    else {
      val base = str2Currency(currencies(0))
      val quote = str2Currency(currencies(1))

      if (base.isEmpty || quote.isEmpty)
        None
      else
        Some(CurrencyPair(base.get, quote.get))
    }
  }

  // 通貨名から通貨クラスに変換
  def str2Currency(str: String): Option[Currency] = {
    // 名前を全通貨から検索
    val hitNames = Currency.allCurrencies().filter(_.names.exists(_ == str))

    // 複数見つかった場合、登録ミスが疑われるのでassert
    assert(hitNames.size <= 1, s"there are currencies that have the same name $str!")

    if (hitNames.size == 1)
      Some(hitNames.head)
    else {
      // 見つからなかった場合、登録されていない通貨なので、エラー出力のみでNoneを返す
      System.err.println(s"there is no currency named $str!")
      None
    }
  }

}
