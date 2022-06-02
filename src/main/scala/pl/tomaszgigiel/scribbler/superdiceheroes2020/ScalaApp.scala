package pl.tomaszgigiel.scribbler.superdiceheroes2020

import cats.effect.{ExitCode, IO, IOApp, Resource}

object ScalaApp extends IOApp {
  val info: IO[Unit] = IO(println("ok (scala)"))

  override def run(args: List[String]): IO[ExitCode] =
    sameThread().as(ExitCode.Success)

  private def sameThread() = for {
    _ <- info
  } yield ()
}
