package michalz.edgen.model.specific

sealed trait Sex

object Sex {

  case object Male extends Sex

  case object Female extends Sex

}
