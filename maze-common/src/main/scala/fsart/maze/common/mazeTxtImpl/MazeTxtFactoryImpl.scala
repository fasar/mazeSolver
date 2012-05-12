package fsart.maze.common.mazeTxtImpl

import fsart.maze.common.model.Maze
import fsart.maze.common.MazeFactory

import scala.reflect.BeanProperty

import java.io.File;


/**
 *  Maze factory implementation.
 *
 *  This class is a container. It's used to store where maze are.
 *  Use addMaze from dir to scan a dir and add maze file in this object.
 *  Use getMazeNames to get what maze you can get, and getMaze to get
 *  a maze.
 *
 *  The Name of a maze is from the file name.
 *
 *  @author    Fabien Sartor
 *  @version   1.0
 */
class MazeTxtFactoryImpl extends MazeFactory {
  
  @BeanProperty var listPathMaze: List[File] = Nil

  def this(path:String) = {this(); addMazesFromDir(path)}

  /**
   * @see   fsart.maze.common.MazeFactory.getMazeNames
   */
  def getMazeNames(): List[String] = {
    listPathMaze map {
      e => e.getName() //.split("\\.")(0)
    }
  }

   /**
   * @see   fsart.maze.common.MazeFactory.getMaze
   */
  override def getMaze(name: String): Option[Maze] = {
    val fic:Option[File] = listPathMaze.find {e => e.getName == name}
    fic flatMap getMazeFromFile
  }

  /**
   * This function is used to get a maze from a file
   */
  def getMazeFromFile(fic:File): Option[Maze] = {
    try {
      if(!fic.exists() || !fic.isFile() || !fic.canRead) return None
      val list:List[String] = TxtToStrList.convert(fic)
      if(list.size==0) return None
      Some(MazeTxtExtractor.extractMazeFromTxt(list))
    } catch {
      case _ => None
    }
  }

  /**
   * Add a path dir where mazes files can be found.
   */
  def addMazesFromDir(path: String) {
    var file:File = new File(path)
    if(file.exists() && file.isDirectory()) {
      for (x <- file.listFiles() if x.getName.endsWith(".maze"))
      {
        addMazeFile(x)
      }
    }
  }


  /**
   * Add a maze file in this container
   */
  def addMazeFile(f:File):Unit = {
    listPathMaze = f::listPathMaze
  }

}
