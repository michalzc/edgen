package michalz.edgen.model.specific

sealed trait Age

object Age {
  case object Kid extends Age
  case object Young extends Age
  case object Adult extends Age
  case object Old extends Age
}
