
package fsart.maze.common.model

import scala.collection.immutable._


/**
 * Represent a Maze
 *
 * Parameter is an Array of Array of Cell.
 * Let Y:Array[Array[Cell]], Y is row and contains X:Array[Cell].
 * X represent a horizontal array of Cell, it is the column
 *
 * @author: Fabien Sartor
 * @version: 1.0
 */
class Maze(val maze: Array[Array[Cell]]) {

  var start:Option[Floor] = None
  var end:Option[Floor] = None

  def sizey:Int = maze.size
  def sizex:Int = if(maze.size>0) maze(0).size else 0

  def setStart(c:Floor) { start = Some(c) }

  def setEnd(c:Floor) { end = Some(c)}


  def getCell(c: Coord): Option[Cell] = {
    getCell(c.x, c.y)
  }

  def getCell(x:Int, y:Int): Option[Cell] = {
    if( (0 until sizex contains x) && (0 until sizey contains y) )
      Option(maze(y)(x))
    else
      None
  }


  def getNorthCell(c: Cell): Option[Cell] = {
    (c.coord.x, c.coord.y) match {
      case (_,y) if(y<=0) => None
      case (x,y) => getCell(x,y-1)
    }
  }

  def getEastCell(c: Cell): Option[Cell] = {
    (c.coord.x, c.coord.y) match {
      case (x,_) if(x>=sizex-1) => None
      case (x,y) => getCell(x+1,y)
    }
  }

  def getSouthCell(c: Cell): Option[Cell] = {
    (c.coord.x, c.coord.y) match {
      case (_,y) if(y>=sizey-1) => None
      case (x,y) => getCell(x,y+1)
    }
  }

  def getWestCell(c: Cell): Option[Cell] = {
    (c.coord.x, c.coord.y) match {
      case (x,_) if(x<=0) => None
      case (x,y) => getCell(x-1,y)
    }
  }

  def getAroundCell(cell:Cell):List[Cell]= {
    val list:List[Option[Cell]] = this.getNorthCell(cell)::this.getEastCell(cell)::this.getSouthCell(cell)::this.getWestCell(cell)::Nil
    val res:List[Cell] =
      for(x:Option[Cell] <- list ; y:Cell <- x)
      yield { y }
    res
  }


  def getStringMaze():String = {
    val res:Seq[String] =
    for(x <- 0 until sizex)
    yield {
      val tmp:Seq[String] =
      for(y <- 0 until sizey)
      yield {
        getCell(x,y).toString +", "
      }
      tmp.mkString + "\n"
    }
    res.mkString
  }
}
