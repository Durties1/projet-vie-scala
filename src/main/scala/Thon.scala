import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

final case class Thon(var x: Double,var y: Double,var dx: Double,var dy: Double, var direction: Direction) {
  val circle = new Circle{
    centerX = x
    centerY = y
    radius = 10
    fill = Color.Blue
  }

  def move(screenWidth: Double, screenHeight: Double): Unit = {
    direction match {
      case Direction.North => y -= dy
      case Direction.South => y += dy
      case Direction.East => x += dx
      case Direction.West => x -= dx
      case Direction.NorthEast => { x += dx; y -= dy }
      case Direction.NorthWest => { x -= dx; y -= dy }
      case Direction.SouthEast => { x += dx; y += dy }
      case Direction.SouthWest => { x -= dx; y += dy }
    }

    if (x < 0 || x > screenWidth) dx = -dx
    if (y < 0 || y > screenHeight) dy = -dy

    circle.centerX = x
    circle.centerY = y
  }
}