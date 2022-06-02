package pl.tomaszgigiel.scribbler.replace

import com.typesafe.scalalogging.Logger
import org.apache.commons.io.FileUtils

import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import scala.collection.mutable.ListBuffer

class ReplacerFileVisitor extends SimpleFileVisitor[Path] {
  val logger: Logger = Logger("ReplacerFileVisitor")

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

  def replace(before: String, after: String): Unit = {
    files.foreach(replaceInContent(_, before, after))
    files.foreach(replaceInFileName(_, before, after))
    dirs.sortBy(x => -x.toAbsolutePath.toString.length).foreach(replaceInDirectoryName(_, before, after))
    logger.info("ok")
  }

  private def replaceInContent(file: Path, before: String, after: String): Unit = {
    logger.info(file.getFileName.toString)
    val content = FileUtils.readFileToString(file.toFile, StandardCharsets.UTF_8)
    val content2 = content.replace(before, after)
    FileUtils.writeStringToFile(file.toFile, content2, StandardCharsets.UTF_8)
  }

  private def replaceInFileName(file: Path, before: String, after: String): Unit = {
    logger.info(file.getFileName.toString)
    val fileNameOld = file.getFileName.toString
    val fileNameNew = fileNameOld.replace(before, after)
    if fileNameOld != fileNameNew then {
      val target = Paths.get(file.getParent.toAbsolutePath.toString, fileNameNew)
      FileUtils.moveFile(file.toFile, target.toFile)
    }
  }

  private def replaceInDirectoryName(dir: Path, before: String, after: String): Unit = {
    logger.info(dir.getFileName.toString)
    val dirNameOld = dir.getFileName.toString
    val dirNameNew = dirNameOld.replace(before, after)
    if dirNameOld != dirNameNew then {
      val target = Paths.get(dir.getParent.toAbsolutePath.toString, dirNameNew)
      FileUtils.moveDirectory(dir.toFile, target.toFile)
    }
  }
}
