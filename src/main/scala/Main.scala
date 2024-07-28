import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color
import javafx.animation.AnimationTimer

object Main extends JFXApp3 {
  val gridWidth = 200
  val gridHeight = 100
  val cellSize = 5

  val environment = Environment(gridWidth, gridHeight)

  def draw(canvas: Canvas, environment: Environment): Unit = {
    val gc = canvas.graphicsContext2D
    gc.fill = Color.White
    gc.fillRect(0, 0, gridWidth * cellSize, gridHeight * cellSize)

    environment.thons.foreach { thon =>
      gc.fill = Color.Blue
      gc.fillRect(thon.x * cellSize, thon.y * cellSize, cellSize, cellSize)
    }

    environment.requins.foreach { requin =>
      gc.fill = Color.Red
      gc.fillRect(requin.x * cellSize, requin.y * cellSize, cellSize, cellSize)
    }
  }

  def runProgram(canvas: Canvas): Unit = {
    def loop(lastUpdateTime: Long): Unit = {
      new AnimationTimer {
        override def handle(now: Long): Unit = {
          if (now - lastUpdateTime >= 0.25e9) { // 0.25 second
            environment.update()
            draw(canvas, environment)
            stop()
            loop(now)
          }
        }
      }.start()
    }
    loop(System.nanoTime())
  }

  override def start(): Unit = {
    val canvas = new Canvas(gridWidth * cellSize, gridHeight * cellSize)
    val mainScene = new Scene(gridWidth * cellSize, gridHeight * cellSize) {
      content = canvas
    }

    stage = new JFXApp3.PrimaryStage {
      title = "Wator Simulation"
      scene = mainScene
    }

    runProgram(canvas)
  }
}