package michalz.edgen.parsers

import fastparse.NoWhitespace._
import fastparse._
import michalz.edgen.model.specific.Attribute

object AttributeDSL {
  def attributeName[_: P]: P[String] = CharIn("A-Z").rep(min = 3, max = 3).!
  def attribute[_: P]: P[Attribute] = attributeName.flatMap { attrName =>
    Attribute.fromString(attrName) match {
      case Some(attr) => Pass(attr)
      case None => Fail(s"$attrName it's not a valid attribute")
    }
  }
}
