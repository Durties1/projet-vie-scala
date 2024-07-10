import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color._
import scalafx.stage.Screen

object Main extends JFXApp3 {

  val nTunas = 20
  val nSharks = 5


  override def start(): Unit = {
    val (screenWidth, screenHeight) = (Screen.primary.visualBounds.width, Screen.primary.visualBounds.height)
    stage = new PrimaryStage {
      title = "Jeu de la vie"
      width = screenWidth
      height = screenHeight
      scene = new Scene {
        fill = White
      }
    }
  }

}