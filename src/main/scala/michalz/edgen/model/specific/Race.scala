package michalz.edgen.model.specific

sealed trait Race

object Race {
  case object Obsidian extends Race
  case object Troll extends Race
  case object Orc extends Race
  case object Human extends Race
  case object Dwarf extends Race
  case object TSkrang extends Race
  case object Elf extends Race
  case object Windling extends Race

  def all: Seq[Race] = Seq(
    Obsidian,
    Troll,
    Orc,
    Human,
    Dwarf,
    TSkrang,
    Elf,
    Windling
  )

  def fromString(name: String): Option[Race] =
    all.find(_.toString == name)
}
