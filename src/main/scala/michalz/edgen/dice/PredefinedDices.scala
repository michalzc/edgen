package michalz.edgen.dice

object PredefinedDices {
  val D6: DiceExpression = Dice.num(6)
  val `2D6`: DiceExpression = Dice.num(6, 2)
  val `3D6`: DiceExpression = Dice.num(6, 3)
}
