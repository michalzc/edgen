package michalz.edgen.dice

import cats.syntax.option._
import fastparse.Parsed.{Failure, Success}
import fastparse.parse
import org.scalatest.Inside
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

class DiceDSLSpec extends AnyFlatSpec with TableDrivenPropertyChecks with Inside with Matchers {

  val numberSpecData = Table(
    ("input", "result"),
    ("1", 1.some),
    ("11", 11.some),
    ("21", 21.some),
    ("0", 0.some),
    ("00", 0.some),
    ("08", 8.some),
    ("a1", none),
    ("1a", 1.some),
    ("1a1", 1.some),
    ("a1a", none),
    ("2 2", 2.some)
  )

  behavior of "Number parser"
  forAll(numberSpecData) { (input, expectedResult) =>
    it should s"parse $input correctly" in {

      val result = parse(input, DiceDSL.number(_))
      expectedResult match {
        case Some(expectedValue) =>
          inside(result) {
            case Success(`expectedValue`, _) => succeed
          }

        case None =>
          inside(result) {
            case Failure(_, _, _) =>
              succeed
          }
      }
    }
  }

  val diceTable = Table(
    ("input", "result"),
    ("d6", Dice.num(6, 1).some),
    ("2d6", Dice.num(6, 2).some),
    ("22d4", Dice.num(4, 22).some),
    ("d[1..5]", Dice.range(1, 5, 1).some),
    ("2d[2..3]", Dice.range(from = 2, to = 3, multipler = 2).some),
    ("d[0..0]", none),
    ("d[2..1]", none),
    ("d[1..1]", Dice.range(from = 1, to = 1, multipler = 1).some),
    ("d[0..1]", Dice.range(from = 0, to = 1, multipler = 1).some)
  )

  behavior of "Dice parser"
  forAll(diceTable) { (input, expectedResult) =>
    it should s"parse $input correctly" in {
      val result = parse(input, DiceDSL.dice(_))
      expectedResult match {
        case Some(exptectedDice) =>
          inside(result) {
            case Success(`exptectedDice`, _) =>
          }

        case None =>
          inside(result) {
            case Failure(_, _, _) => succeed
          }
      }
    }
  }

  val diceExpressionTable = Table(
    ("input", "result"),
    ("d6", Dice.num(6, 1)),
    ("2d4", Dice.num(4, 2)),
    ("3d8 + 2d6 - 2", DiceExpression.minus(DiceExpression.plus(Dice.num(8, 3), Dice.num(6,2)) ,Const(2))),
    ("6d6k3 + 2", DiceExpression.plus(Dice.keepDice(1, 6, 6, 3), Const(2)))
  )

  behavior of "DiceExpression parser"
  forAll(diceExpressionTable) { (input, expected) =>

    it should s"parse $input correctly" in {
      val result = parse(input, DiceDSL.diceExpression(_))
      inside(result) {
        case Success(`expected`, _) =>
          succeed
      }
    }

  }

}
