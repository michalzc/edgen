package michalz.edgen

import scala.util.Random

package object dice {

  sealed trait DiceExpression {
    def eval()(implicit rnd: Random): (Seq[Seq[Int]], Int)
  }

  case class Plus(left: DiceExpression, right: DiceExpression) extends DiceExpression {
    override def eval()(implicit rnd: Random): (Seq[Seq[Int]], Int) = {
      val (leftRolls, leftResult)   = left.eval()
      val (rightRolls, rightResult) = right.eval()
      (leftRolls ++ rightRolls, leftResult + rightResult)
    }
  }

  case class Minus(left: DiceExpression, right: DiceExpression) extends DiceExpression {
    override def eval()(implicit rnd: Random): (Seq[Seq[Int]], Int) = {
      val (leftRolls, leftResult)   = left.eval()
      val (rightRolls, rightResult) = right.eval()
      (leftRolls ++ rightRolls, leftResult - rightResult)
    }
  }

  sealed trait Dice extends DiceExpression {
    def from: Int
    def to: Int
    def multipler: Int

    protected def singleRoll()(implicit rnd: Random): Int =
      rnd.between(from, to + 1)

    protected def roll()(implicit rnd: Random): Seq[Int] =
      Seq.fill(multipler)(singleRoll())

    override def eval()(implicit rnd: Random): (Seq[Seq[Int]], Int) = {
      val rolls = roll()
      Seq(rolls) -> rolls.sum
    }
  }

  case class BasicDice(from: Int, to: Int, multipler: Int) extends Dice

  case class KeepDice(from: Int, to: Int, multipler: Int, keep: Int) extends Dice with Keep {
    override protected def roll()(implicit rnd: Random): Seq[Int] = keepRoll(super.roll())
  }

  case class ExplodeDice(from: Int, to: Int, multipler: Int) extends Dice with Explode {
    override protected def roll()(implicit rnd: Random): Seq[Int] = explodeRoll()
  }

  case class KeepExplodeDice(from: Int, to: Int, multipler: Int, keep: Int) extends Dice with Keep with Explode {
    override protected def roll()(implicit rnd: Random): Seq[Int] = keepRoll(explodeRoll())
  }

  trait Keep {
    def keep: Int

    protected def keepRoll(roll: Seq[Int])(implicit rnd: Random): Seq[Int] =
      roll.sorted(Ordering[Int].reverse).take(keep)
  }

  trait Explode {
    self: Dice =>
    protected def explodeRoll()(implicit rnd: Random): Seq[Int] = {
      def run(rolls: Seq[Int], rerolls: Int): Seq[Int] = {
        if (rerolls == 0) {
          rolls
        } else {
          val newRolls = Seq.fill(rerolls)(singleRoll())
          run(rolls ++ newRolls, newRolls.count(_ == to))
        }
      }

      val rs = Seq.fill(multipler)(singleRoll())
      run(rs, rs.count(_ == to))
    }
  }

  case class Const(num: Int) extends DiceExpression {
    override def eval()(implicit rnd: Random): (Seq[Seq[Int]], Int) = Seq.empty -> num
  }

  object Dice {
    def num(n: Int, multipler: Int = 1): Dice                             = BasicDice(1, n, multipler)
    def range(from: Int, to: Int, multipler: Int = 1): Dice               = BasicDice(from, to, multipler)
    def keepDice(source: Dice, keep: Int): Dice                       = KeepDice(source.from, source.to, source.multipler, keep)
    def keepDice(from: Int, to: Int, multipler: Int, keep: Int): Dice = KeepDice(from, to, multipler, keep)
    def explodeDice(source: Dice): Dice                               = ExplodeDice(source.from, source.to, source.multipler)
    def explodeDice(from: Int, to: Int, multipler: Int): Dice         = ExplodeDice(from, to, multipler)
    def keepExplodeDice(source: Dice, keep: Int): Dice                = KeepExplodeDice(source.from, source.to, source.multipler, keep)
    def keepExplodeDice(from: Int, to: Int, multipler: Int, keep: Int): Dice =
      KeepExplodeDice(from, to, multipler, keep)
  }

  object DiceExpression {
    def plus(left: DiceExpression, right: DiceExpression): DiceExpression  = Plus(left, right)
    def minus(left: DiceExpression, right: DiceExpression): DiceExpression = Minus(left, right)

    def build(start: DiceExpression, elems: Seq[(String, DiceExpression)]): DiceExpression = elems.foldLeft(start) {
      case (left, ("+", right)) => DiceExpression.plus(left, right)
      case (left, ("-", right)) => DiceExpression.minus(left, right)
    }
  }

}
