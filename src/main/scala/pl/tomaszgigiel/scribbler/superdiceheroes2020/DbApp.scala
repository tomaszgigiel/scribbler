package pl.tomaszgigiel.scribbler.superdiceheroes2020

import cats.effect.{ExitCode, IO, IOApp, Resource}
import doobie.h2.H2Transactor
import doobie.hikari.*
import doobie.implicits.*
import doobie.util.ExecutionContexts
import doobie.{ConnectionIO, Update}

import java.net.URL

object DbApp extends IOApp {
  type FileItemRow = (String, String, String)
  type FileItem = (String, String)

  private def info(x: Any): IO[Unit] = IO(println(x))

  private val _TODO_data = List[FileItem](
    ("a.txt", "line 1"),
    ("a.txt", "line 2"),
    ("a.txt", "line 3"))

  override def run(args: List[String]): IO[ExitCode] =
    transactor.use { xa =>
      for {
        version <- sql"SELECT H2VERSION()".query[String].unique.transact(xa)
        free <- sql"SELECT MEMORY_FREE()".query[String].unique.transact(xa)
        _ <- info(version)
        _ <- info(free)
        _ <- sql"INSERT INTO scribbler_schema.table_chunks (file, chunk) VALUES ('file 1', 'line 1')".update.run.transact(xa)
        - <- insertMany(_TODO_data).transact(xa)
        select <- sql"SELECT id, file, chunk FROM scribbler_schema.table_chunks".query[FileItemRow].to[List].transact(xa)
        _ <- info(select)
        _ <- info("ok (scala)")
      } yield ExitCode.Success
    }

  val transactor: Resource[IO, H2Transactor[IO]] = {
    val initFile: URL = getClass.getResource("/H2/init.sql")
    val url = s"jdbc:h2:mem:test;INIT=RUNSCRIPT FROM '$initFile';DB_CLOSE_DELAY=0"
    val user = """sa"""
    val password = """sa"""
    val poolSize = 32

    for {
      ce <- ExecutionContexts.fixedThreadPool[IO](poolSize)
      xa <- H2Transactor.newH2Transactor[IO](url, user, password, ce)
    } yield xa
  }

  def insertMany(xs: List[FileItem]): ConnectionIO[Int] = {
    val sql = "INSERT INTO scribbler_schema.table_chunks (file, chunk) VALUES (?, ?)"
    Update[FileItem](sql).updateMany(xs)
  }
}
