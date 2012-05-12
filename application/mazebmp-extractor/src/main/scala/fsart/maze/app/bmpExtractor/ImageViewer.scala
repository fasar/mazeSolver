package fsart.maze.app.bmpExtractor

import scala.swing.event.WindowClosing
import java.awt.image.BufferedImage
import scala.swing.UIElement._
import java.awt.{Graphics2D, Dimension, Graphics}
import swing._
import javax.swing.ScrollPaneConstants

class ImageViewer {

  private var imgpriv:Option[BufferedImage] = None

  def img_=(image:BufferedImage) {
    imgpriv = Option(image)
    container.preferredSize = new Dimension(image.getWidth+20, image.getHeight+60)
    top.repaint()
  }

  def img:Option[BufferedImage] = {imgpriv}



  private var columnSigpriv:Option[BufferedImage] = None

  def columnSig_=(image:BufferedImage) {
    columnSigpriv = Option(image)
    val height = if(imgpriv.isDefined) imgpriv.get.getHeight + image.getHeight + 40 else  image.getHeight + 20
    val width:Int =  if(imgpriv.isDefined) top.preferredSize.getWidth.toInt else  image.getWidth + 20
    container.preferredSize = new Dimension(width, height)
    top.repaint()
    top.pack()
  }

  def columnSig:Option[BufferedImage] = {columnSigpriv}


  private var lineSigpriv:Option[BufferedImage] = None

  def lineSig_=(image:BufferedImage) {
    lineSigpriv = Option(image)
    val height = if(imgpriv.isDefined) top.preferredSize.getHeight.toInt else image.getHeight
    val width:Int =  if(imgpriv.isDefined) imgpriv.get.getWidth + image.getWidth + 20 else  image.getWidth + 20
    container.preferredSize = new Dimension(width, height)
    top.repaint()
    top.pack()
  }

  def lineSig:Option[BufferedImage] = {lineSigpriv}



  val top = new MainFrame {
    title = "Maze Viewer"
    preferredSize = new Dimension(800, 600)
    contents = container
    //preferredSize = new Dimension(img.getWidth+20, img.getHeight+60)
    reactions += {
      case WindowClosing(src) => sys.exit(0)
    }
  }

  lazy val container = new FlowPanel(FlowPanel.Alignment.Left)(myPane) {

  }

  lazy val myPane = new Panel() { myPaneObj =>
    override def paintComponent(g:Graphics2D) {
      super.paintComponent(g);
      if(img.isDefined) g.drawImage(img.get, 0, 0, null)
      if(columnSig.isDefined) g.drawImage(columnSig.get, 0, img.get.getHeight+10, null)
      if(lineSig.isDefined) g.drawImage(lineSig.get, img.get.getWidth+10, 0, null)
      if(img.isDefined) { myPaneObj.preferredSize = new Dimension(img.get.getWidth*2+40, img.get.getHeight*2+40) }
    }

  }

  def displayImage(image:BufferedImage) {
     container.contents += new Panel() {
       override def paintComponent(g:Graphics2D) {
         super.paintComponent(g);
         g.drawImage(image, 0, 0, null)
       }
       preferredSize = new Dimension(image.getWidth, image.getHeight)
     }
     repaint()
  }

  def addPanel(pan:Panel) {
     container.contents += pan
     repaint()
  }


  def startup() {
    top.pack()
    top.visible = true
  }

  def repaint() {
    top.pack()
  }

}


object ImageViewer {
  def apply(image: BufferedImage): ImageViewer = {
    var gui: ImageViewer = new ImageViewer()
    Swing.onEDT( gui.startup() )
    gui.img = image
    gui
  }

}


