package pl.tomaszgigiel.scribbler.superdiceheroes2020

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.config.{Config, ConfigFactory}
import edu.stanford.nlp.pipeline.{CoreDocument, StanfordCoreNLP}
import org.apache.commons.collections4.IteratorUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import pl.tomaszgigiel.scribbler.superdiceheroes2020.Step04SentencesApp.sentencesByStanford

import java.io.File
import java.text.BreakIterator
import java.util.{Locale, Properties}
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters.*
import scala.jdk.javaapi.CollectionConverters.*

object Step04SentencesApp extends IOApp {
  // TODO: args
  private val conf: Config = ConfigFactory.parseFile(new File("""/home/tomasz/Documents/workspace/scribbler/src/test/resources/super-dice-heroes-2020/super-dice-heroes-2020.properties"""))

  override def run(args: List[String]): IO[ExitCode] = sameThread().as(ExitCode.Success)

  private def sameThread() = for {
    corrected <- IO(FileUtils.readFileToString(new File(conf.getString("""superdiceheroes2020.step03.rules.forum.corrected.destination""")), "UTF-8"))
    sentencesByJdk <- IO(sentencesByJdk(corrected))
    sentencesByStanford <- IO(sentencesByStanford(corrected))
    _ <- IO(FileUtils.writeStringToFile(new File(conf.getString("""superdiceheroes2020.step04.rules.forum.corrected.sentences.byjdk.destination""")), sentencesByJdk.mkString("\n"), "UTF-8"))
    _ <- IO(FileUtils.writeStringToFile(new File(conf.getString("""superdiceheroes2020.rules.forum.manual.corrected.sentences.bystanford.destination""")), sentencesByStanford.mkString("\n"), "UTF-8"))
    _ <- info("ok (scala)")
  } yield ()

  private def info(s: String): IO[Unit] = IO(println(s))

  private def sentencesByJdk(s: String): List[String] = {
    val result = new ListBuffer[String]()
    val iterator = BreakIterator.getSentenceInstance(Locale.US)
    iterator.setText(s)

    var start = iterator.first
    var end = iterator.next
    while end != BreakIterator.DONE do {
      result += s.substring(start, end)
      start = end
      end = iterator.next
    }
    result.toList
  }

  private def sentencesByStanford(s: String): List[String] = {
    val properties = new Properties()
    properties.setProperty("annotators", "tokenize,ssplit")
    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(properties)
    val doc: CoreDocument = new CoreDocument(s)
    pipeline.annotate(doc)
    doc.sentences().asScala.map(x => x.text()).toList
  }

  private def info(ss: List[String]): IO[Unit] = IO(ss.map(println))
}
