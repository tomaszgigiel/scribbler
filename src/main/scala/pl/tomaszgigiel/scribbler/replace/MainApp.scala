package pl.tomaszgigiel.scribbler.replace

import cats.effect.IOApp
import com.typesafe.scalalogging.Logger

import java.nio.file.{Files, Path, Paths}
import scala.io.Source
import scala.util.Using

@main
def main(): Unit =
  val logger = Logger("replace")

  val replacer = new ReplacerFileVisitor()
  Files.walkFileTree(Paths.get("/home/tomasz/Documents/workspace/scribbler"), replacer)
  replacer.replace("scribbler", "scribbler")
  logger.info("ok")
