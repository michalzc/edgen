import michalz.edgen.generator.specific._

import scala.util.Random

implicit val rnd = new Random()

(1 to 1000).map(_ => raceGenerator()).groupBy(identity).view.mapValues(_.size).toMap
