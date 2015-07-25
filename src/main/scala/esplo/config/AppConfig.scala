package esplo.config

trait AppConfig {
  val phantomJSPath: String

  // DB settings
  val mongoHost: String
  val mongoPort: Int
  val mongoDBName: String
  val mongoCollectionName: String
}
