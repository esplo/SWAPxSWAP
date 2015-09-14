package esplo

import esplo.config.Settings
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities


class DriverManager(val phantomJSPath: String) {

  val caps = new DesiredCapabilities()
  // 存在しないパスの場合は、ドライバ生成時に例外を投げる
  if(!phantomJSPath.isEmpty)  // 空の場合は設定しない
    caps.setCapability("phantomjs.binary.path", phantomJSPath)

  val driver = new PhantomJSDriver(caps)

}
