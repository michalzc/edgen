package michalz.edgen.dice

import fastparse.parse
import cats.syntax.either._
import fastparse.Parsed.{Failure, Success}

import scala.util.Random

object DiceRoller {

  type DiceResult = (Seq[Seq[Int]], Int)
  type DiceResultOrError = Either[String, DiceResult]

  def rollDice(diceString: String)(implicit rnd: Random): DiceResultOrError = {

    parse(diceString, DiceDSL.diceExpression(_)) match {
      case Success(dice, _) =>
        dice.eval().asRight

      case Failure(errorString, index, extra) =>
        errorString.asLeft
    }
  }
}
