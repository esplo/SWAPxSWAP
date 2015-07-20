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
  val phantomJSPath: String = """path/to/your/phantomjs.exe"""
}
```

設定可能な項目を、以下に列挙します。

* phantomJSPath
    * PhantomJSのexeファイルへのパス

### 実行
プロジェクトルートで、以下のコマンドを実行します。
```bash
sbt run
```
または
```bash
activator run
```
