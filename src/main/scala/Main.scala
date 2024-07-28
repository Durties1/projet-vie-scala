import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color
import javafx.animation.AnimationTimer

object Main extends JFXApp3 {
  val width = 200
  val height = 100
  val size = 7

  val environment = Environment(width, height)

  def draw(canvas: Canvas, environment: Environment): Unit = {
    val gc = canvas.graphicsContext2D
    gc.fill = Color.White
    gc.fillRect(0, 0, width * size, height * size)

    environment.thons.foreach { thon =>
      gc.fill = Color.Blue
      gc.fillRect(thon.x * size, thon.y * size, size, size)
    }

    environment.requins.foreach { requin =>
      gc.fill = Color.Red
      gc.fillRect(requin.x * size, requin.y * size, size, size)
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
    val canvas = new Canvas(width * size, height * size)
    val mainScene = new Scene(width * size, height * size) {
      content = canvas
    }

    stage = new JFXApp3.PrimaryStage {
      title = "Jeu de la vie"
      scene = mainScene
    }

    runProgram(canvas)
  }
}