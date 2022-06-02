package pl.tomaszgigiel.scribbler.superdiceheroes2020

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.config.{Config, ConfigFactory}

import java.io.File

object Step02ManualApp extends IOApp {
  // TODO: args
  private val conf: Config = ConfigFactory.parseFile(new File("""/home/tomasz/Documents/workspace/scribbler/src/test/resources/super-dice-heroes-2020/super-dice-heroes-2020.properties"""))

  override def run(args: List[String]): IO[ExitCode] = sameThread().as(ExitCode.Success)

  private def sameThread() = for {
    _ <- info("save: " + conf.getString("""superdiceheroes2020.step02.rules.page.url""") + " to: " + conf.getString("""superdiceheroes2020.step02.rules.destination"""))
    _ <- info("save: " + conf.getString("""superdiceheroes2020.step02.board.page.url""") + " to: " + conf.getString("""superdiceheroes2020.step02.board.destination"""))
    _ <- info("save: " + conf.getString("""superdiceheroes2020.step02.rules.forum.url""") + " to: " + conf.getString("""superdiceheroes2020.step02.rules.forum.destination"""))
    _ <- info("ok (scala)")
  } yield ()

  private def info(s: String): IO[Unit] = IO(println(s))
}
