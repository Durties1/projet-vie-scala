import scala.util.Random

final case class Environment(screenWidth: Double, screenHeight: Double) {
  var thons: List[Thon] = List.fill(20)(Thon(Random.nextDouble() * screenWidth, Random.nextDouble() * screenHeight, 5, 5, Direction.randomDirection, 1))
  var requins: List[Requin] = List.fill(5)(Requin(Random.nextDouble() * screenWidth, Random.nextDouble() * screenHeight, 5, 5, Direction.randomDirection, 10, 5))

  def update(): Unit = {
    val newThons = thons.flatMap { thon =>
      val (updatedThon, newThon) = thon.moveRandomlyAndBreed(screenWidth, screenHeight, this)
      thons = thons.map(t => if (t == thon) updatedThon else t)
      newThon
    }

    val newRequins = scala.collection.mutable.ListBuffer[Requin]()

    requins = requins.flatMap { requin =>
      val (updatedRequin, newRequin) = requin.moveAndBreed(screenWidth, screenHeight, this)
      updatedRequin.foreach(newRequins += _)
      newRequin
    }

    thons = thons.filterNot(thon => requins.exists(requin => Math.abs(thon.x - requin.x) <= 1.0 && Math.abs(thon.y - requin.y) <= 1.0))
    thons ++= newThons
    requins ++= newRequins
  }

  def findFreePositionsAround(x: Double, y: Double): List[(Double, Double)] = {
    val directions = List(-1.0, 0.0, 1.0).flatMap(dx => List(-1.0, 0.0, 1.0).map(dy => (dx, dy))).filterNot(_ == (0.0, 0.0))

    directions.flatMap { case (dx, dy) =>
      val newX = x + dx
      val newY = y + dy
      if (newX >= 0 && newX <= screenWidth && newY >= 0 && newY <= screenHeight && !isOccupied(newX, newY)) {
        Some((newX, newY))
      } else None
    }
  }

  private def isOccupied(x: Double, y: Double): Boolean = {
    thons.exists(thon => Math.abs(thon.x - x) <= 1.0 && Math.abs(thon.y - x) <= 1.0) ||
      requins.exists(requin => Math.abs(requin.x - x) <= 1.0 && Math.abs(requin.y - x) <= 1.0)
  }
}