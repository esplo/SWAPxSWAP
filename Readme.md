# SWAPxSWAP

## 概要
各業者のサイトをスクレイピングし、スワップポイントを抽出します。
高スワップ業者探しや、日々の変動を追跡するのに役立ちます。

ちなみに、某番組とは名前が似ているだけで一切関係はございません。

<a name="setting">
## 設定ファイル
プロジェクトルートに"app.conf"という名前で設定ファイルを用意します。
設定例を以下に掲載します。

```Scala
import esplo.config.AppConfig
import scala.sys._

new AppConfig {
  override val phantomJSPath: String = """/phantomjs/bin/phantomjs"""
  override val mongoHost: String = sys.env.getOrElse("DB_PORT_27017_TCP_ADDR", "localhost")
  override val mongoPort: Int = sys.env.getOrElse("DB_PORT_27017_TCP_PORT", "27101").toInt
  override val mongoDBName: String = "swap-swap"
  override val mongoCollectionName: String = "SwapInfo"
}
```

設定可能な項目を、以下に列挙します。

* phantomJSPath
    * PhantomJSのexeファイルへのパス
* mongoHost
    * MongoDBのホスト名
* mongoPort
    * MongoDBのポート
* mongoDBName
    * MongoDBで使用するDB名
* mongoCollectionName
    * MongoDBで使用するコレクション名


## セットアップと実行
MongoDBのサーバーを立て、それに合わせた[設定ファイル](#setting)を配置します。
その後、プロジェクトルートで以下のコマンドを実行します。

```bash
sbt run
```
または
```bash
activator run
```
