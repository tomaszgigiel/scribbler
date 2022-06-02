package pl.tomaszgigiel.scribbler.cognitivebiases

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.commons.io.FileUtils

import java.io.File
import java.net.URL
import scala.io.BufferedSource

object Step01DownloadFilesApp extends IOApp {
  // TODO: args
  private val conf: Config = ConfigFactory.parseFile(new File("""/home/tomasz/Documents/workspace/scribbler/src/test/resources/cognitive-biases/cognitive-biases.properties"""))

  override def run(args: List[String]): IO[ExitCode] = sameThread().as(ExitCode.Success)

  // TODO: Error: This link has expired or is invalid. Please go back to the file page for a fresh link.
  // TODO: continue after exception
  private def sameThread() = for {
    _ <- download(conf.getString("""cognitivebiases.step01.url"""), conf.getString("""cognitivebiases.step01.destination"""))
    _ <- info("ok (scala)")
  } yield ()

  private def info(s: String): IO[Unit] = IO(println(s))

  private def download(url: String, destinationFile: String): IO[Unit] = IO(FileUtils.copyURLToFile(new URL(url), new File(destinationFile)))
}
