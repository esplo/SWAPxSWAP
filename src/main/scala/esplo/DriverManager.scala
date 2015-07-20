package esplo

import com.twitter.util.Eval
import esplo.config.AppConfig
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities

class DriverManager {

  val conf = Eval[AppConfig](new java.io.File("./AppConfig.scala"))

  val caps = new DesiredCapabilities()
  // 存在しないパスの場合は、ドライバ生成時に例外を投げる
  caps.setCapability("phantomjs.binary.path", conf.phantomJSPath)

  val driver = new PhantomJSDriver(caps)

}
