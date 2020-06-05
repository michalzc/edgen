package michalz.edgen.dice

case class DiceResult(
                       allRolls: List[Int],
                       significantRolls: List[Int],
                       result: List[Int]
                     )
