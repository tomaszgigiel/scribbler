package pl.tomaszgigiel.scribbler.filmgroundhogday1993

import java.util.{ArrayList, Collection}
import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters.*

case class Item(question: Seq[String], answer: Seq[String], tag: Seq[String], source: Seq[String])

def toFreeMarkerItem(item: Item) = {
  val result = java.util.HashMap[String, java.util.Collection[String]]()
  result.put("question", item.question.asJavaCollection)
  result.put("answer", item.answer.asJavaCollection)
  result.put("tag", item.tag.asJavaCollection)
  result.put("source", item.source.asJavaCollection)
  result
}

def toFreeMarkerModel(items: List[Item]) = {
  val model = java.util.HashMap[String, Object]()
  model.put("items", items.map(toFreeMarkerItem).asJavaCollection)
  model
}
