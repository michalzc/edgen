package michalz.edgen.generator.generic

import michalz.edgen.dice.DiceExpression

import scala.util.Random

sealed trait GenericGenerator[O] {
  def run()(implicit rnd: Random): O
}

sealed trait DiceGenerator[O] extends GenericGenerator[O] {
  def diceExpression: DiceExpression
}

case class DiceValueGenerator(diceExpression: DiceExpression) extends DiceGenerator[Int] {
  override def run()(implicit rnd: Random): Int = diceExpression.eval()._2
}