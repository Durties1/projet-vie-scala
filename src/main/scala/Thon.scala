import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

final case class Thon(x: Double, y: Double, dx: Double, dy: Double, direction: Direction, tBreed: Int) {
  val circle = new Circle {
    centerX = x
    centerY = y
    radius = 4
    fill = Color.Blue
  }

  def moveRandomlyAndBreed(screenWidth: Double, screenHeight: Double, environment: Environment): (Thon, Option[Thon]) = {
    val newTBreed = if (tBreed > 0) tBreed - 1 else tBreed

    val possibleDirections = Direction.allDirections.filter(canMove(_, screenWidth, screenHeight, environment))
    val (newX, newY) = if (possibleDirections.nonEmpty) {
      val newDirection = possibleDirections(scala.util.Random.nextInt(possibleDirections.length))
      moveInDirection(newDirection, screenWidth, screenHeight)
    } else (x, y)

    val newThon = this.copy(x = newX, y = newY, tBreed = newTBreed)

    if (newTBreed == 0) {
      (newThon.copy(tBreed = 10), Some(newThon.copy(tBreed = 10)))
    } else {
      (newThon, None)
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

  private def moveInDirection(direction: Direction, screenWidth: Double, screenHeight: Double): (Double, Double) = {
    direction match {
      case Direction.North => (x, y - dy)
      case Direction.South => (x, y + dy)
      case Direction.East => (x + dx, y)
      case Direction.West => (x - dx, y)
      case Direction.NorthEast => (x + dx, y - dy)
      case Direction.NorthWest => (x - dx, y - dy)
      case Direction.SouthEast => (x + dx, y + dy)
      case Direction.SouthWest => (x - dx, y + dy)
    }
  }
}