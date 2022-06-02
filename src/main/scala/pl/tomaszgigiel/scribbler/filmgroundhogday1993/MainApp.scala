package pl.tomaszgigiel.scribbler.filmgroundhogday1993

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
  val logger = Logger("filmgroundhogday1993")
  val conf: Config = ConfigFactory.parseFile(new File(properties))

  val pathStep01SubtitlesEn = conf.getString("filmgroundhogday1993.step01.subtitles.en")
  val pathStep02SentencesEn = conf.getString("filmgroundhogday1993.step02.sentences.en")
  val pathStep03SentencesEnManual = conf.getString("filmgroundhogday1993.step03.sentences.en.manual")
  val pathStep04SentencesPlManual = conf.getString("filmgroundhogday1993.step04.sentences.pl.manual")
  val pathStep05EnPlTxt = conf.getString("filmgroundhogday1993.step05.en.pl.txt")
  val pathStep05TemplateAnki = conf.getString("filmgroundhogday1993.step05.template.anki")

  def onlySentence(s: String) = s matches ".*[a-zA-Z].*"

  def prepareStep02SentencesEn = Using.Manager { use =>
    val step01SubtitlesEn = use(Source.fromFile(pathStep01SubtitlesEn)).getLines.toList
    val step02SentencesEn = new File(pathStep02SentencesEn)
    val bw = use(new BufferedWriter(new FileWriter(step02SentencesEn)))

    val sentences = step01SubtitlesEn.filter(x => onlySentence(x))
    logger.info("sentences.length = " + sentences.length)
    sentences.foreach(x => bw.write(x + "\n"))
  }

  def prepareStep05EnPlTxt = Using.Manager { use =>
    val step03SentencesEnManual = use(Source.fromFile(pathStep03SentencesEnManual)).getLines.mkString("\n").split("\n\n").toList
    val step04SentencesPlManual = use(Source.fromFile(pathStep04SentencesPlManual)).getLines.mkString("\n").split("\n\n").toList
    val step05EnPlTxt = new File(pathStep05EnPlTxt)
    val bw = use(new BufferedWriter(new FileWriter(step05EnPlTxt)))

    // freemarker
    val cfg = new Configuration(Configuration.VERSION_2_3_31)
    cfg.setDirectoryForTemplateLoading(new File("/"))
    cfg.setDefaultEncoding("UTF-8")
    cfg.setLocale(Locale.US)
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
    val template = cfg.getTemplate(pathStep05TemplateAnki)

    val ab = step03SentencesEnManual zip step04SentencesPlManual
    val ba = step04SentencesPlManual zip step03SentencesEnManual
    val merged = ab ++ ba
    val fishes = merged.map(x => Item(x._1.split("\n"), x._2.split("\n"), List("film-groundhog-day-1993"), List("internet")))

    val output = new StringWriter
    template.process(toFreeMarkerModel(fishes), output)
    bw.write(output.toString)
  }

  prepareStep02SentencesEn
  prepareStep05EnPlTxt
  logger.info("ok (scala)")
