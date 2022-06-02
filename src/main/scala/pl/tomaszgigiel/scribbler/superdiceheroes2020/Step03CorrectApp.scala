package pl.tomaszgigiel.scribbler.superdiceheroes2020

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils

import java.io.File

object Step03CorrectApp extends IOApp {
  // TODO: args
  private val conf: Config = ConfigFactory.parseFile(new File("""/home/tomasz/Documents/workspace/scribbler/src/test/resources/super-dice-heroes-2020/super-dice-heroes-2020.properties"""))

  override def run(args: List[String]): IO[ExitCode] = sameThread().as(ExitCode.Success)

  private def sameThread() = for {
    rules <- IO(FileUtils.readFileToString(new File(conf.getString("""superdiceheroes2020.step02.rules.forum.destination""")), "UTF-8"))
    corrected <- IO(StringUtils.replaceEach(rules, Array[String]("“", "”", "’"), Array[String]("\"", "\"", "'")))
    _ <- IO(FileUtils.writeStringToFile(new File(conf.getString("""superdiceheroes2020.step03.rules.forum.corrected.destination""")), corrected, "UTF-8"))
    _ <- info("ok (scala)")
  } yield ()

  private def info(s: String): IO[Unit] = IO(println(s))
}
