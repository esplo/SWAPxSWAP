package esplo.config


trait AppConfig {
  val phantomJSPath: String

  /**
   * DB settings
   * this system will use mongoURI if that is not empty
   */

  // full qualified URI
  // mongodb://username:password@server:port/database
  val mongoURI: String

  // use these settings if mongoURI is empty
  val mongoHost: String
  val mongoPort: Int
  val mongoDBName: String

  val mongoCollectionName: String
}
