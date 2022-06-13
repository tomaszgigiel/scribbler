package pl.tomaszgigiel.scribbler.cryptor

import com.typesafe.scalalogging.Logger
import org.zeroturnaround.zip.ZipUtil

import java.io.File

object Compressor {
  val logger: Logger = Logger(getClass)

  def pack(source: String, zipped: String) : Unit = {
    logger.debug("pack")
    ZipUtil.pack(new File(source), new File(zipped))
  }

  def unpack(zipped: String, unzipped: String) : Unit = {
    logger.debug("unpack")
    ZipUtil.unpack(new File(zipped), new File(unzipped))
  }
}
