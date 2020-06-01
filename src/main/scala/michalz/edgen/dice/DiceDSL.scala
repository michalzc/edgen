package michalz.edgen.dice

import fastparse._
import fastparse.NoWhitespace._

object DiceDSL {

  def rangeFun(rangeS: (String, String)): (Int, Int) = rangeS._1.toInt -> rangeS._2.toInt

  def diceSymbol[_: P]: P[Unit] = CharIn("dDkK")
  def mNegNumber[_: P] = (P("-").!.? ~ CharsWhileIn("0-9").rep.!).map { case (op, num) => (op.getOrElse("") + num).toInt }
  def number[_: P]: P[Int]       = P(CharsWhileIn("0-9").rep(1).!).map(_.toInt)
  def range[_: P]: P[(Int, Int)] = (P("[") ~ mNegNumber ~ P("..") ~ mNegNumber ~ P("]")).filter{ case (from, to) => from < to}
  def numDice[_: P]: P[Dice]     = (number.? ~ diceSymbol ~ number).map { case (mul, num) => Dice.num(num, mul.getOrElse(1)) }
  def rangeDice[_: P]: P[Dice] = (number.? ~ diceSymbol ~ range).map {
    case (mul, (from, to)) => Dice.range(from, to, mul.getOrElse(1))
  }
  def keepOperator[_: P]: P[Int] = P("k") ~ number
  def explodeOperator[_: P]: P[Unit] = P("e")
  def keepExplodeOperator[_: P]: P[Int] = P("K") ~ number

  def diceEnd[_: P]: P[Unit] = CharIn("+\\- ") | End
  def dice[_: P]: P[Dice] = rangeDice | numDice
  def keepDice[_: P]: P[Dice] = (dice ~ keepOperator).filter{ case (d, keep) => d.multipler > keep}.map { case (dice, keep) => Dice.keepDice(dice, keep)}
  def explodeDice[_: P]: P[Dice] = (dice ~ explodeOperator).map(Dice.explodeDice)
  def keepExplodeDice[_: P]: P[Dice] = (dice ~ keepExplodeOperator).filter{ case (d, keep) => d.multipler > keep }.map { case (dice, keep) => Dice.keepExplodeDice(dice, keep) }
  def expressionSeparator[_: P]: P[String] = P(" ").rep ~ CharIn("+\\-").! ~ P(" ").rep
  def constExpression[_: P] = number.map(Const.apply)
  def diceOrConst[_: P]: P[DiceExpression] = (keepExplodeDice | explodeDice | keepDice | dice | constExpression) ~ &(diceEnd)

  def diceExpression[_: P]: P[DiceExpression] = P ( diceOrConst ~ (expressionSeparator ~ diceOrConst).rep(0) ~ End )
    .opaque("Invalid dice expression")
    .map { case (start, parts) =>DiceExpression.build(start, parts) }
}
