package pl.tomaszgigiel.scribbler.cryptor

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.Logger
import org.apache.commons.io.FileUtils

import java.io.File

@main
def main(properties: String): Unit =
  val logger = Logger("replace")

  val conf: Config = ConfigFactory.parseFile(new File(properties))
  val confidential = conf.getString("cryptor.confidential")
  val zipped = conf.getString("cryptor.zipped")
  val encrypted = conf.getString("cryptor.encrypted")
  val decrypted = conf.getString("cryptor.decrypted")
  val password = conf.getString("cryptor.password")

  Compressor.pack(confidential, zipped)
  Cryptor.encrypt(zipped, encrypted, password)
  Cryptor.decrypt(encrypted, decrypted, password)

  if !FileUtils.contentEquals(new File(zipped), new File(decrypted)) then throw new Exception("contents not equals")

  logger.info("ok")
