package esplo.config

object Settings {
  val appConfig = Eval.fromFileName[AppConfig]("./app.conf")
}
