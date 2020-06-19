package michalz.edgen.parsers

import michalz.edgen.model.specific.Race

object RaceParser {

  def parser(input: String): Either[String, Race] =
    Race.
      all
      .find(_.tag == input.toLowerCase())
      .toRight(s"$input it's not a valid Race")

}
