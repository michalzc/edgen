package michalz.edgen

package object utils {

  def histogram[A](source: Iterable[A], scale: Int): String = {
    val base = source.groupBy(identity).view.mapValues(_.size).toList
    val max = base.map(_._2).max
    val unit = scale.toDouble / max

    base.map{ case (item, number) =>
      val f = math.round(unit * number).toInt
      val bar = List.fill(f)("#").mkString.padTo(scale, '.')
      s""
    }

    ???
  }

}
