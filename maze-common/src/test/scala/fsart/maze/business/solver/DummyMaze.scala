package fsart.maze.business.solver

import fsart.maze.common.model._
import scala.collection.immutable.HashMap

import org.scalatest.Suite


class DummyMaze extends Maze(Array(Array[Cell]())) {

  override def sizex = 10
  override def sizey = 10

  override def getCell(x: Int, y: Int): Option[Cell] = {

    (x, y) match {
      case (0,0) => Some(Floor(0,0))
      case (x,5) if x>= 0 && x<sizex => Some(Floor(x,5))
      case (5,y) if y>= 0 && y<sizey => Some(Floor(5,y))
      case (x,y) => Some(Wall(x, y))
    }
  }
}





