package esplo

import esplo.config.{Settings, AppConfig}
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities

class DriverManager {

  val caps = new DesiredCapabilities()
  // 存在しないパスの場合は、ドライバ生成時に例外を投げる
  caps.setCapability("phantomjs.binary.path", Settings.appConfig.phantomJSPath)

  val driver = new PhantomJSDriver(caps)

}
