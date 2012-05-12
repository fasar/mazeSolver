package fsart.maze.app.bmpExtractor
/**
 * Tools to play with image
 *
 *  displayMetadata source : http://johnbokma.com/java/obtaining-image-metadata.html
 *
 */


import javax.imageio.metadata._
import java.awt.color.ColorSpace
import org.w3c.dom._
import java.awt.image.{AffineTransformOp, BufferedImageOp, BufferedImage, ColorConvertOp}
import java.awt.geom.AffineTransform
import fsart.maze.imagetools.CannyEdgeDetector

object ImageTools {


  /**
   * Display metadata of the image
   *
   * Code from java.
   * Source : http://johnbokma.com/java/obtaining-image-metadata.html
   */
  def displayMetadata(node:Node) {
    def displayMetadata(node:Node, level:Int ) {
        def indent(level:Int) {
          for (i <- 1 until level)
            System.out.print("  ");
        }

        // print open tag of element
        indent(level);
        print("<" + node.getNodeName() )
        val map:NamedNodeMap = node.getAttributes();
        if (map != null) {
            // print attribute values
            val length:Int = map.getLength()
            for (i <- 0 until length) {
                val attr:Node = map.item(i)
                print(" " + attr.getNodeName() +
                       "=\"" + attr.getNodeValue() + "\"")
            }
        }

        var child:Node = node.getFirstChild()
        if (child == null) {
            // no children, so close element and return
            println("/>");
        } else {
          // children, so close current tag
          System.out.println(">");
          while (child != null) {
              // print children recursively
              displayMetadata(child, level + 1)
              child = child.getNextSibling()
          }
          // print close tag of element
          indent(level);
          println("</" + node.getNodeName() + ">")
        }
    }
    displayMetadata(node, 0)
  }



  /**
   * Clone the image
   */
   def cloneImage(img:BufferedImage):BufferedImage = {
    val res:BufferedImage =  new BufferedImage(img.getWidth, img.getHeight, BufferedImage.TYPE_BYTE_GRAY) //BufferedImage.TYPE_INT_ARGB)
    res.setData(img.getData)
    res
   }

  /**
   * Rotate 45 degrees around image center
   */
  def rotate(img:BufferedImage, degrees:Int):BufferedImage = {
    val filter:BufferedImageOp = new AffineTransformOp(AffineTransform.getRotateInstance(scala.math.Pi*degrees/(-180), img.getHeight/2, img.getWidth/2), AffineTransformOp.TYPE_NEAREST_NEIGHBOR)
    filter.filter(img, null)
  }



  /**
   * Transform the image in grey colors and provide a new image
   * Result is a new image.
   */
  def toGrey(img:BufferedImage):BufferedImage = {
    val cs:ColorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY)
    val op:ColorConvertOp = new ColorConvertOp(cs, null)
    op.filter(img, null)
  }

  /**
   * Transform the image in RBG colors.
   * Result is a new image.
   */
  def toRVB(img:BufferedImage):BufferedImage = {
    val cs:ColorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB)
    val op:ColorConvertOp = new ColorConvertOp(cs, null)
    op.filter(img, null)
  }


}
