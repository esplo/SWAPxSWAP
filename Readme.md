# SWAPxSWAP

## 概要
各業者のサイトをスクレイピングし、スワップポイントを抽出します。
高スワップ業者探しや、日々の変動を追跡するのに役立ちます。

ちなみに、某番組とは名前が似ているだけで一切関係はございません。

## 使い方

### 設定
プロジェクトルートに"AppConfig.scala"という名前で設定ファイルを用意します。
内容は以下の通り。

```Scala
import esplo.config.AppConfig

new AppConfig {
  override val phantomJSPath: String = """/path/to/phantomjs.exe"""
  override val mongoHost: String = "localhost"
  override val mongoPort: Int = 27017
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

### 実行
プロジェクトルートで、以下のコマンドを実行します。
```bash
sbt run
```
または
```bash
activator run
```
