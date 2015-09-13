package esplo.config

import java.io.File


class Settings(val conf: File) {
  val appConfig = Eval.fromFile[AppConfig](conf)
}
