package pl.tomaszgigiel.scribbler.superdiceheroes2020

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.config.{Config, ConfigFactory}
import pl.tomaszgigiel.scribbler.superdiceheroes2020.Step04SentencesApp.conf

import java.io.File

object Step06TranslateYourselfApp extends IOApp {
  // TODO: args
  private val conf: Config = ConfigFactory.parseFile(new File("""/home/tomasz/Documents/workspace/scribbler/src/test/resources/super-dice-heroes-2020/super-dice-heroes-2020.properties"""))

  override def run(args: List[String]): IO[ExitCode] = sameThread().as(ExitCode.Success)

  private def sameThread() = for {
    _ <- info("translate it yourself: " + conf.getString("""superdiceheroes2020.step05.rules.forum.corrected.sentences.bystanford.translation.destination""") + " and save to: " + conf.getString("""superdiceheroes2020.step05.rules.forum.corrected.sentences.bystanford.translation.yourself.destination"""))
    _ <- info("ok (scala)")
  } yield ()

  private def info(s: String): IO[Unit] = IO(println(s))
}
