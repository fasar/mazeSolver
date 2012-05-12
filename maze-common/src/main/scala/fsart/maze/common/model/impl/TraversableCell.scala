package fsart.maze.common.model.impl

import fsart.maze.common.model._


/**
 * Implementation of a TraversableNode with Cell and Maze
 *
 * @see       fsart.maze.common.model.TraversableNode
 *
 * @auther:   Fabien Sartor
 * @version:  2.0
 */
class TraversableCell(maze:Maze, val node:Floor) extends TraversableNode[TraversableCell] with CardinalPointNode[TraversableCell] {

   /**
   * @see       fsart.maze.common.model.TraversableNode#aroundNodes
   */
  def aroundNodes:List[TraversableCell] = {
    val floorCells = maze.getAroundCell(node) withFilter { _.isInstanceOf[Floor]}
    floorCells map { floor => TraversableCell(maze, floor.asInstanceOf[Floor]) }
  }

  /**
   *  @see       fsart.maze.common.model.TraversableNode#weightToGoTo
   */
  override def weightToGoTo(nodeDst:TraversableCell): Double = {1.0}

  /**
   *  @see       fsart.maze.common.model.TraversableNode#estimateWeightToGoTo
   */
  override def estimateWeightToGoTo(nodeDst:TraversableCell): Double = {
    val c1:Coord = node.coord
    val c2:Coord = nodeDst.node.coord
    (c2.x-c1.x) + (c2.y-c1.y)
  }



  /**
   * @see       fsart.maze.common.model.CardinalPointNode#northNode
   */
  def northNode: Option[TraversableCell] = {
    val cello:Option[Cell] = maze.getNorthCell(node)
    for(cell <- cello if cell.isInstanceOf[Floor])
    yield { TraversableCell(maze, cell.asInstanceOf[Floor]) }
  }

  /**
   * @see       fsart.maze.common.model.CardinalPointNode#westNode
   */
  def westNode: Option[TraversableCell] = {
    val cello:Option[Cell] = maze.getWestCell(node)
    for(cell <- cello if cell.isInstanceOf[Floor])
    yield { TraversableCell(maze, cell.asInstanceOf[Floor]) }
  }

  /**
   * @see       fsart.maze.common.model.CardinalPointNode#southNode
   */
  def southNode: Option[TraversableCell] = {
    val cello:Option[Cell] = maze.getSouthCell(node)
    for(cell <- cello if cell.isInstanceOf[Floor])
    yield { TraversableCell(maze, cell.asInstanceOf[Floor]) }
  }

  /**
   * @see       fsart.maze.common.model.CardinalPointNode#eastNode
   */
  def eastNode: Option[TraversableCell] = {
    val cello:Option[Cell] = maze.getEastCell(node)
    for(cell <- cello if cell.isInstanceOf[Floor])
    yield { TraversableCell(maze, cell.asInstanceOf[Floor]) }
  }



  override def equals(that : Any): Boolean = {
    that match {
      case d: TraversableCell =>
        d.canEqual(this)
        this.node == d.node
      case _ => false
    }
  }

  def canEqual(other: Any) = other.isInstanceOf[Cell]

  override def hashCode: Int = node.hashCode
}


object TraversableCell {
  def apply(maze:Maze, cell:Floor) = new TraversableCell(maze, cell)


  def getListNodes(list: List[TraversableCell]):List[Floor] = {
    list map { _.node }
  }


}
