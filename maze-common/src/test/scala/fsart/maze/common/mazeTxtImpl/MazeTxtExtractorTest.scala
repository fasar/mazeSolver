package fsart.maze.common.mazeTxtImpl



import fsart.maze.common.model._
import org.scalatest.Suite

import java.net.URL
import java.io.File


class MazeTxtExtractorTest extends Suite {
  var maze:Maze = null
  var list:List[String] = Nil

  def reinit() {

    var url:URL = this.getClass().getResource("./maze1.maze")
    var fic:File = new File(url.toURI)
    
    list = TxtToStrList.convert(fic)
    println(list)
    assert(list!=Nil, "file doesn't contain data : " + url.toString) 
    maze = MazeTxtExtractor.extractMazeFromTxt(list)
    assert(maze.sizex==23, "Maze doesn't have good v size. It has got : " + maze.sizex)
    assert(maze.sizey==6, "Maze doesn't have good h size. It has got :" + maze.sizey)
  }

  def testNoMaze() {
    maze = MazeTxtExtractor.extractMazeFromTxt(List(""))
    assert(maze.sizex==0, "Maze doesn't have good v size")
    assert(maze.sizey==0, "Maze doesn't have good h size")
  }

  def testNilMaze() {
    maze = MazeTxtExtractor.extractMazeFromTxt(Nil)
    assert(maze.sizex==0, "Maze doesn't have good v size")
    assert(maze.sizey==0, "Maze doesn't have good h size")
  }

  def testWallMaze() {
    reinit()
    var c:Option[Cell] = maze.getCell(0,0)
    c match {
      case Some(x:Wall) => assert(true)
      case _ => assert(false, "It must be a Wall")
    }
  }


  def testFloorMaze() {
    reinit()
    var c:Option[Cell] = maze.getCell(0,1)

    c match {
      case Some(x:Floor) => assert(true)
      case _ => assert(false, "It must be a Floor")
    }
  }

  def testGetNorth() {
    reinit()
    var c:Option[Cell] = maze.getCell(0,maze.sizey-1)
    if(c.isEmpty) assert(false, "can't get the cell")
    maze.getNorthCell(c.get) match {
      case Some(x:Floor) => assert(true)
      case Some(x:Wall) => assert(false, "It must bee a floor")
      case None => assert(false, "I can't get smth")
    }
  }

  def testFailGetNorth() {
    reinit()
    var c:Option[Cell] = maze.getCell(0,0)
    if(c.isEmpty) assert(false, "can't get the cell")
    maze.getNorthCell(c.get) match {
      case None => assert(true)
      case _ => assert(false, "It must be nothing")
    }
  }


  def testGetEast() {
    reinit()
    var c:Option[Cell] = maze.getCell(0,1)
    if(c.isEmpty) assert(false, "can't get the cell")
    maze.getEastCell(c.get) match {
      case None => assert(false, "I can't get smth")
      case _ => assert(true)
    }
  }

  def testFailGetEast() {
    reinit()
    var c:Option[Cell] = maze.getCell(maze.sizex-1, maze.sizey-1)
    if(c.isEmpty) assert(false, "can't get the cell")
    maze.getEastCell(c.get) match {
      case None => assert(true)
      case _ => assert(false, "It must be nothing")
    }
  }


  def testGetSouth() {
    reinit()
    var c:Option[Cell] = maze.getCell(0,0)
    if(c.isEmpty) assert(false, "can't get the cell")
    maze.getSouthCell(c.get) match {
      case None => assert(false, "I can't get smth")
      case _ => assert(true)
    }
  }

  def testFailGetSouth() {
    reinit()
    var c:Option[Cell] = maze.getCell(0, maze.sizey-1)
    if(c.isEmpty) assert(false, "can't get the cell")
    maze.getSouthCell(c.get) match {
      case None => assert(true)
      case _ => assert(false, "It must be nothing")
    }
  }


  def testGetWeast() {
    reinit()
    var c:Option[Cell] = maze.getCell(maze.sizex-1,1)
    assert(c.isDefined,"can't get the cell")
    maze.getWestCell(c.get) match {
      case None => assert(false, "I can't get smth")
      case Some(x) => assert(x==Floor(maze.sizex-2,1), "I can't get the good cell. I get : " + x)
    }
  }

  def testFailGetWeast() {
    reinit()
    var c:Option[Cell] = maze.getCell(0,0)
    if(c.isEmpty) assert(false, "can't get the cell")
    maze.getWestCell(c.get) match {
      case None => assert(true)
      case _ => assert(false, "It must be nothing")
    }
  }

   def testGetStart() {
    reinit()
    var c:Option[Cell] = maze.start
    val ref =  Floor(0, 1)
    c match {
      case None => assert(false, "can't get the cell")
      case Some(x) => assert(c.get == ref, "error in getStart cell. I get : " + x+ ", and I wanted " + ref)
    }
  }

   def testGetEnd() {
    reinit()
    var c:Option[Cell] = maze.end
    val ref = Floor(21, 1)
    c match {
      case None => assert(false, "can't get the end cell")
      case Some(x) => assert(x == ref, "error in getEnd cell. I get : " + x + ", and I wanted " + ref)
    }
  }
}

