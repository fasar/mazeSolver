package fsart.maze.common.mazeTxtImpl

import fsart.maze.common.model._


/**
 * Enum od characters signification in maze file.
 *
 *
 * @author    fabien sartor
 * @version   1.0
 *
 */
object MazeTxtConstante {
    val listCar =  START_CHAR::END_CHAR::PATH_CHAR::WALL_CHAR::Nil
    val START_CHAR:Char = 's'
    val END_CHAR:Char = 'e'
    val PATH_CHAR:Char = '_'
    val WALL_CHAR:Char = '#'
}

/**
 * Maze Extractor
 *
 * This class can extract a maze from a file
 *
 * @author    fabien sartor
 * @version   1.0
 *
 */
object MazeTxtExtractor {
   import MazeTxtConstante._

  /**
   * Extract a Maze from a maze file.
   *
   * A maze file is build with :
   *  '''#''' as wall, '''_''' as floor, '''s''' as the start cell and '''e''' as the end cell.
   */
  def extractMazeFromTxt(txts: List[String]): Maze = {
    var startCell:Option[Floor] = None
    var endCell:Option[Floor] = None

    val maze:Iterable[Array[Cell]] =
      for(  (line, nline) <- txts.filter(_ != "").zipWithIndex if line != "" )
      yield {
        val cells:Iterable[Cell] =
          for(  (car,ncar) <- line.zipWithIndex ;
                cell <- carToCell(car, ncar, nline) )
          yield {
            if(car==START_CHAR && cell.isInstanceOf[Floor]) startCell = Option(cell.asInstanceOf[Floor])
            if(car==END_CHAR && cell.isInstanceOf[Floor]) endCell = Option(cell.asInstanceOf[Floor])
            cell
          }
        cells.toArray
      }

    val res = new Maze(maze.toArray)
    res.end = endCell
    res.start= startCell
    res
  }


  /**
   * convert a character as cell.
   */
  def carToCell(c: Char, x:Int, y:Int):Option[Cell] = {
    c match {
      case WALL_CHAR => Some(Wall(x,y))
      case PATH_CHAR => Some(Floor(x,y))
      case START_CHAR => Some(Floor(x,y))
      case END_CHAR  => Some(Floor(x,y))
      case _ => None
    }
  }


}
