package michalz.edgen.generator.generic

case class GeneratorRange(from: Int, to: Int) {
  def isMatching(number: Int): Boolean = from <= number && number < to
}
