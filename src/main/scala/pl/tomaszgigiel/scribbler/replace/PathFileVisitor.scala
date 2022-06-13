package pl.tomaszgigiel.scribbler.replace

import com.typesafe.scalalogging.Logger
import org.apache.commons.io.FileUtils

import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import scala.collection.mutable.ListBuffer

class PathFileVisitor extends SimpleFileVisitor[Path] {
  val logger: Logger = Logger(getClass)

  var dirs: ListBuffer[Path] = new ListBuffer[Path]()
  var files: ListBuffer[Path] = new ListBuffer[Path]()

  override def postVisitDirectory(dir: Path, ex: IOException): FileVisitResult = {
    logger.debug(dir.getFileName.toString)
    dirs += dir
    FileVisitResult.CONTINUE
  }

  override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult = {
    logger.debug(dir.getFileName.toString)
    FileVisitResult.CONTINUE
  }

  override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {
    logger.debug(file.getFileName.toString)
    files += file
    FileVisitResult.CONTINUE
  }

  override def visitFileFailed(file: Path, ex: IOException): FileVisitResult = {
    logger.error(ex.getLocalizedMessage)
    FileVisitResult.CONTINUE
  }

  def getDirs: ListBuffer[Path] = {
    logger.debug(dirs.toString)
    dirs.sortBy(x => -x.toAbsolutePath.toString.length)
  }

  def getFiles: ListBuffer[Path] = {
    logger.debug(files.toString)
    files
  }

  def init(dir: String): Unit = {
    logger.debug(files.toString)
    Files.walkFileTree(Paths.get(dir), this)
  }
}
