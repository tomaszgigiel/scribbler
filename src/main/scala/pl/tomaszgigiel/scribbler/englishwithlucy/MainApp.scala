package pl.tomaszgigiel.scribbler.englishwithlucy

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.Logger
import freemarker.template.{Configuration, TemplateExceptionHandler}
import org.apache.commons.io.FileUtils

import java.io.{BufferedWriter, File, FileWriter, StringWriter}
import java.util
import java.util.{HashMap, Locale}
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.jdk.CollectionConverters.*
import scala.util.Using

@main
def main(properties: String): Unit =
  val logger = Logger("englishwithlucy")
  val conf: Config = ConfigFactory.parseFile(new File(properties))

  val a = conf.getString("ankinator.a")
  val b = conf.getString("ankinator.b")
  val ts = conf.getString("ankinator.tags")
  val s = conf.getString("ankinator.source")
  val t = conf.getString("ankinator.template.anki")
  val o = conf.getString("ankinator.out")

  def perform = Using.Manager { use =>
    val aa = use(Source.fromFile(a)).getLines.mkString("\n").split("\n\n").toList
    val bb = use(Source.fromFile(b)).getLines.mkString("\n").split("\n\n").toList
    val out = new File(o)
    val bw = use(new BufferedWriter(new FileWriter(out)))

    // freemarker
    val cfg = new Configuration(Configuration.VERSION_2_3_31)
    cfg.setDirectoryForTemplateLoading(new File("/"))
    cfg.setDefaultEncoding("UTF-8")
    cfg.setLocale(Locale.US)
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
    val template = cfg.getTemplate(t)

    val ab = aa zip bb
    val ba = bb zip aa
    val merged = ab ++ ba
    val fishes = merged.map(x => Item(x._1.split("\n"), x._2.split("\n"), ts.split(" "), List(s)))

    val output = new StringWriter
    template.process(toFreeMarkerModel(fishes), output)
    bw.write(output.toString)
  }

  perform
  logger.info("ok (scala)")
