package fsart.maze.common.mazeTxtImpl


import fsart.maze.common.model._
import java.io.{BufferedWriter, FileWriter}


/**
 * Maze Extractor
 *
 * This object backups a maze to a file
 *
 * @author    fabien sartor
 * @version   1.0
 *
 */
object MazeTxtSav {
  import MazeTxtConstante._

  /**
   * Transform a maze in txt.
   * This output can be used in MazeTxtExtractor.extractMazeFromTxt
   */
  def mazeToTxt(maze:Maze):List[String] = {

    def getCharFromCell(cell:Cell):Char = {
      cell match {
        case c:Wall => WALL_CHAR
        case c:Floor =>
          if (maze.start == Some(c)) START_CHAR
          else if (maze.end == Some(c)) END_CHAR
          else PATH_CHAR
      }
    }

    val res:Seq[String] =
    for(x <- 0 until maze.sizex)
    yield {
      val tmp:Seq[String] =
      for(y <- 0 until maze.sizey; cell <- maze.getCell(x,y))
      yield {
        getCharFromCell(cell).toString
      }
      tmp.mkString
    }

    res.toList
  }


  def saveToFile(path: String, maze:Maze) {
    val fw:FileWriter = new FileWriter(path, false)
    val output:BufferedWriter = new BufferedWriter(fw)
    output.write(mazeToTxt(maze).mkString("\n"))
    output.flush()
    output.close()
  }
}
