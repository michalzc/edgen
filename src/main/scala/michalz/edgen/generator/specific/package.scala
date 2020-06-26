package michalz.edgen.generator

import michalz.edgen.model.specific.{Age, NPC, Race, Sex}
import org.scalacheck.Gen

package object specific {

  val races: Map[Vector[Int], Race] = Map(
    Vector(3) -> Race.Obsidian,
    (4 to 5).toVector -> Race.Troll,
    (6 to 7).toVector -> Race.Orc,
    (8 to 12).toVector -> Race.Human,
    (13 to 14).toVector -> Race.Dwarf,
    Vector(15) -> Race.Tskrang,
    (16 to 17).toVector -> Race.Elf,
    Vector(18) -> Race.Windling
  )

  val npcRace: Gen[Race] = DiceGen.diceGen(3, 6).map { num =>
    races.find(_._1.contains(num)).map(_._2).get
  }

  val sexWilderness: Gen[Sex] = DiceGen.diceGen(3).map { num =>
    if (num == 1) Sex.Female
    else Sex.Male
  }

  val sexSettlement: Gen[Sex] = DiceGen.diceGen(2).map { num =>
    if (num == 1) Sex.Female
    else Sex.Male
  }

  val ageWilderness: Gen[Age] = DiceGen.diceGen(6).map {
    case 1 => Age.Young
    case n if n <= 5 => Age.Adult
    case n if n <= 6 => Age.Old
  }

  val ageSettlement: Gen[Age] = DiceGen.diceGen(6).map {
    case 1 => Age.Kid
    case 2 => Age.Young
    case n if n <= 4 => Age.Adult
    case _ => Age.Old
  }

  def isAdept(race: Race): Gen[Boolean] = DiceGen.diceGen(6).map { num =>
    race match {
      case Race.Human | Race.Orc | Race.Dwarf =>
        num == 6

      case Race.Elf =>
        num >= 4

      case Race.Tskrang | Race.Obsidian | Race.Troll =>
        num >= 5

      case Race.Windling =>
        num >= 3

    }
  }

  def npcSettlement: Gen[NPC] = for {
    race <- npcRace
    sex <- sexSettlement
    age <- ageSettlement
    isAdept <- isAdept(race)
  } yield NPC(race, age, sex, isAdept)
}
