package fsart.maze.app.gui.guiComponents

import swing._
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Color, Dimension}

import fsart.maze.common.model._
import fsart.maze.common.model.impl.TraversableCell
import fsart.maze.business.GraphSolverListener

/**
 * This is the view panel of the maze.
 *
 * Internaly it controls the design of the maze panel
 *
 * @author    fabien sartor
 * @version   1.0
 */
object mazePanel extends Panel {
  private val cellSize = 20

  var alreadyDoneCells:List[Cell] = Nil
  var pathCells:List[Cell] = Nil
  var currentCell:Option[Cell] = None
  var mazeo:Option[Maze] = None

  private var scaleImgp:Double = 1.0
  def scaleImg_= (x:Double) {
    scaleImgp = x
    repaint()
  }
  def scaleImg:Double = scaleImgp


  override def paintComponent(g:Graphics2D) {
    super.paintComponent(g);
    g.scale(scaleImg, scaleImg)
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

    if(maze.start.isDefined && maze.start.get == c) {
      g setColor Color.cyan
      g.fillOval(c.getCoord.x*cellSize+3, c.getCoord.y*cellSize+3 ,cellSize-6, cellSize-5)
    } else if(currentCell.isDefined && currentCell.get == c) {//currentCell.canEqual(c)) {
      g setColor Color.pink
      g.fillOval(c.getCoord.x*cellSize+3, c.getCoord.y*cellSize+3 ,cellSize-6, cellSize-6)
    } else if(maze.end.isDefined && maze.end.get == c) {
      g setColor Color.red
      g.fillOval(c.getCoord.x*cellSize+3, c.getCoord.y*cellSize+3 ,cellSize-6, cellSize-6)
    } else if(pathCells.contains(c)) {
      g setColor Color.green
      g.fillOval(c.getCoord.x*cellSize+3, c.getCoord.y*cellSize+3 ,cellSize-6, cellSize-6)
    } else if(alreadyDoneCells.contains(c)) {
      g setColor Color.gray
      g.fillOval(c.getCoord.x*cellSize+3, c.getCoord.y*cellSize+3 ,cellSize-6, cellSize-6)
    }

  }

  /**
   * This methods paint with some color to
   */
  def setCurrentSolution(currentCell:Option[Cell], path:List[Cell], alreadyDoneCells:List[Cell]) {
    this.alreadyDoneCells = alreadyDoneCells
    this.pathCells = path
    this.currentCell = currentCell
    repaint()
  }



  def setMaze(maze:Maze) {
    this.preferredSize = new Dimension(maze.sizey * cellSize, maze.sizex * cellSize)
    alreadyDoneCells = Nil
    pathCells = Nil
    currentCell = None
    mazeo=Option(maze)
  }
}










