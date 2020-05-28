
import fastparse._
import michalz.edgen.dice.DiceDSL

import scala.util.Random

implicit val rnd = new Random()

parse("5k[-3..-1]", DiceDSL.diceExpression(_)).get.value.eval()
