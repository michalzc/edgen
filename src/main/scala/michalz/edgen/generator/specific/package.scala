package michalz.edgen.generator

import michalz.edgen.dice.PredefinedDices
import michalz.edgen.model.specific.Race

import scala.util.Random

package object specific {

  def raceGenerator()(implicit rnd: Random): Race = {
    val source = List(
      List(3) -> Race.Obsidian,
      (4 to 5) -> Race.Troll,
      (6 to 7) -> Race.Orc,
      (8 to 12) -> Race.Human,
      (13 to 14) -> Race.Dwarf,
      List(15) -> Race.Tskrang,
      (16 to 17) -> Race.Elf,
      (List(18)) -> Race.Windling
    ).flatMap { case (rng, race) => rng.toList.map(_ -> race) }
      .toMap

    source.get(PredefinedDices.`3D6`.eval()._2).get
  }

}
