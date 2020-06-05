package michalz.edgen.model.specific


sealed trait Attribute {
  def name: String
  def `type`: AttributeType
}

object Attribute {

  def all = List(DEX, STR, TOU, PER, WIL, CHA)

  def fromString(s: String): Option[Attribute] =
    all.find(_.toString == s)

  case object DEX extends Attribute {
    override def name: String = "Dexterity"
    override def `type`: AttributeType = AttributeType.Phisical
  }

  case object STR extends Attribute {
    override def name: String = "Strength"
    override def `type`: AttributeType = AttributeType.Phisical
  }

  case object TOU extends Attribute {
    override def name: String = "Toughness"
    override def `type`: AttributeType = AttributeType.Phisical
  }

  case object PER extends Attribute {
    override def name: String = "Perception"
    override def `type`: AttributeType = AttributeType.Mental
  }

  case object WIL extends Attribute {
    override def name: String = "Willpower"
    override def `type`: AttributeType = AttributeType.Mental
  }

  case object CHA extends Attribute {
    override def name: String = "Charisma"
    override def `type`: AttributeType = AttributeType.Mental
  }

}

sealed trait AttributeType

object AttributeType {
  case object Phisical extends AttributeType
  case object Mental extends AttributeType
  def all: List[AttributeType] = List(Phisical, Mental)
}
