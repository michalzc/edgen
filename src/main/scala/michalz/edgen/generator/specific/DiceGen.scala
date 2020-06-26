package michalz.edgen.generator.specific

import org.scalacheck.Gen

object DiceGen {

  def diceGen(mul: Int, dice: Int): Gen[Int] =
    Gen.listOfN(mul, Gen.choose(1,dice)).map(_.sum)

  def diceGen(dice: Int): Gen[Int] =
    Gen.choose(1, dice)

}
