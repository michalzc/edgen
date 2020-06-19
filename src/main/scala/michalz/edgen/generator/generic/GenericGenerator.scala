package michalz.edgen.generator.generic

import michalz.edgen.generator.generic.GenericGenerator.ParserType

class GenericGenerator[T](
  parser: ParserType[T]
) {

}

object GenericGenerator {
  type ParserType[T] = String => Either[String, T]
}