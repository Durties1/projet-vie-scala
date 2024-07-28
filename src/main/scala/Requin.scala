import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

final case class Requin(x: Double, y: Double, dx: Double, dy: Double, direction: Direction, sBreed: Int, sEnergy: Int) {
  val circle = new Circle {
    centerX = x
    centerY = y
    radius = 4
    fill = Color.Red
  }

  def moveAndBreed(screenWidth: Double, screenHeight: Double, environment: Environment): (Option[Requin], Option[Requin]) = {
    val newSBreed = Math.max(0, sBreed - 1)
    val newSEnergy = Math.max(0, sEnergy - 1)

    if (newSEnergy <= 0) {
      return (None, None) // Shark dies if energy is zero or less
    }

    val (newX, newY, updatedEnergy) = environment.thons.find(thon => Math.abs(thon.x - x) <= dx && Math.abs(thon.y - y) <= dy) match {
      case Some(thon) =>
        environment.thons = environment.thons.filterNot(_ == thon)
        (x, y, newSEnergy + 5)
      case None =>
        val possibleDirections = Direction.allDirections.filter(canMove(_, screenWidth, screenHeight, environment))
        if (possibleDirections.nonEmpty) {
          val newDirection = possibleDirections(scala.util.Random.nextInt(possibleDirections.length))
          moveInDirection(newDirection, screenWidth, screenHeight)
        } else (x, y, newSEnergy)
    }

    val newRequin = this.copy(x = newX, y = newY, sBreed = newSBreed, sEnergy = updatedEnergy)

    if (newSBreed <= 0 && updatedEnergy > 0) {
      (Some(newRequin.copy(sBreed = 10)), Some(newRequin.copy(sBreed = 10)))
    } else {
      (Some(newRequin), None)
    }
  }

  private def canMove(direction: Direction, screenWidth: Double, screenHeight: Double, environment: Environment): Boolean = {
    direction match {
      case Direction.North => y - dy >= 0
      case Direction.South => y + dy <= screenHeight
      case Direction.East => x + dx <= screenWidth
      case Direction.West => x - dx >= 0
      case Direction.NorthEast => x + dx <= screenWidth && y - dy >= 0
      case Direction.NorthWest => x - dx >= 0 && y - dy >= 0
      case Direction.SouthEast => x + dx <= screenWidth && y + dy <= screenHeight
      case Direction.SouthWest => x - dx >= 0 && y + dy <= screenHeight
    }
  }

  private def moveInDirection(direction: Direction, screenWidth: Double, screenHeight: Double): (Double, Double, Int) = {
    direction match {
      case Direction.North => (x, y - dy, sEnergy)
      case Direction.South => (x, y + dy, sEnergy)
      case Direction.East => (x + dx, y, sEnergy)
      case Direction.West => (x - dx, y, sEnergy)
      case Direction.NorthEast => (x + dx, y - dy, sEnergy)
      case Direction.NorthWest => (x - dx, y - dy, sEnergy)
      case Direction.SouthEast => (x + dx, y + dy, sEnergy)
      case Direction.SouthWest => (x - dx, y + dy, sEnergy)
    }
  }
}