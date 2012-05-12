package fsart.maze.app.bmpExtractor

/**
 * This is the view panel of the maze.
 *
 * Internaly it controls the design of the maze panel
 *
 * @author    fabien sartor
 * @version   1.0
 */

import swing._
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, Dimension}

import fsart.maze.common.model._
import reflect.BeanProperty
object mazePanel2 extends Panel {
  private val cellSize = 4
  @BeanProperty var mazeo:Option[Maze] = None


  override def paintComponent(g:Graphics2D) {
    super.paintComponent(g);

    if(mazeo.isDefined) {
      val maze = mazeo.get
      for(x<-0 to maze.sizex - 1) {
        for(y<- 0 to maze.sizey - 1) {
          var c = maze.getCell(x,y)
          if(c.isDefined)
            paintCell(g, c.get)
        }
      }
    }
  }

  /**
   * Draw a cell in the panel.
   *
   * @see Cell
   */
  private def paintCell(g:Graphics2D, c:Cell) {
    val maze = mazeo.get
    c match {
      case c:Wall=> g
          g setColor Color.black
          g.fillRect(c.getCoord.x*cellSize, c.getCoord.y*cellSize ,cellSize, cellSize)
      case c:Floor =>
          g setColor Color.white
          g.fillRect(c.getCoord.x*cellSize, c.getCoord.y*cellSize ,cellSize, cellSize)
    }
  }


  def setMaze(maze:Maze) {
    this.preferredSize = new Dimension(maze.sizey * cellSize, maze.sizex * cellSize)
    mazeo=Option(maze)
  }
}










