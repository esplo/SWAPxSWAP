package esplo.config

import com.twitter.util.Eval

object Settings {
  val appConfig = Eval[AppConfig](new java.io.File("./AppConfig.scala"))
}
