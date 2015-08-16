package esplo.config

object Settings {
  val appConfig = Eval.fromFileName[AppConfig]("./AppConfig.scala")
}
