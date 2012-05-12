package fsart.maze.app.gui.guiTrait

import swing._
import java.awt.Color

trait HorizontalLine extends Panel {
  override def paintComponent(g:Graphics2D) {
    super.paintComponent(g)
    g setColor Color.red
    for(x <- 0 until size.height by 20 )
      g.drawLine(0, x, size.width, x)
  }
}










