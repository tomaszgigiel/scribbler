package pl.tomaszgigiel.scribbler.dailystoic50shortrulesforlifefromthestoics

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.commons.io.FileUtils

import java.io.File
import scala.io.Source
import java.io.BufferedWriter
import java.io.FileWriter
import scala.util.Using

@main
def main(properties: String): Unit =
  val conf: Config = ConfigFactory.parseFile(new File(properties))
  val pathA = conf.getString("dailystoic50shortrulesforlifefromthestoics.a")
  val pathB = conf.getString("dailystoic50shortrulesforlifefromthestoics.b")
  val pathOut = conf.getString("dailystoic50shortrulesforlifefromthestoics.out")

  Using.Manager { use =>
    val a = use(Source.fromFile(pathA)).getLines.toList
    val b = use(Source.fromFile(pathB)).getLines.toList
    val out = new File(pathOut)
    val bw = use(new BufferedWriter(new FileWriter(out)))

    val ab = a zip b
    val ba = b zip a
    val merged = ab ++ ba
    println(ab.length)
    println(ba.length)
    println(merged.length)
    merged.foreach(x => bw.write(x._1 + "\t" + x._2 + "\n"))
  }
  println("ok (scala)")
