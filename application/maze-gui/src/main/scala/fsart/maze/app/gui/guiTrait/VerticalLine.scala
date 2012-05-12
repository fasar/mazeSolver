package fsart.maze.app.gui.guiTrait

import swing._
import java.awt.Color

trait VerticalLine extends Panel {
  override def paintComponent(g:Graphics2D) {
    super.paintComponent(g)
    g setColor Color.red
    for(x <- 0 until size.width by 20 )
      g.drawLine(x, 0, x, size.height)
  }
}










