package michalz.edgen.model.specific

sealed trait Race {
  def name: String = toString
  def tag: String = toString.toLowerCase()
}

object Race {
  case object Obsidian extends Race
  case object Troll extends Race
  case object Orc extends Race
  case object Human extends Race
  case object Dwarf extends Race
  case object Tskrang extends Race
  case object Elf extends Race
  case object Windling extends Race

  def all: Seq[Race] = List(
    Obsidian,
    Troll,
    Orc,
    Human,
    Dwarf,
    Tskrang,
    Elf,
    Windling
  )

  def fromString(name: String): Option[Race] =
    all.find(_.toString == name)
}
