package fsart.maze.business

import fsart.maze.common.model._

/**
 * This trait is an interface for listener of GraphSolverPublisher
 *
 * The class that is interested in processing a step of solving
 * implement this interface. The object created with this class
 * is registered with a GraphSolverPublisher object, using the
 * setCurrentSolution of the GraphSolverPublisher object. When a
 * step of solving is done, the GraphSolverPublisher use the
 * setCurrentSolution function of this object.
 *
 * @author  fabien sartor
 * @version 1.0
 */
trait GraphSolverListener[T] {


  /**
   * This function gives three standards arguments for solvers.
   *
   * @param currentCell         the current cell the algo is process
   * @param path                cells the algorithm thinks are good
   * @param alreadyDoneCells    cells already done and not useful
   */
  def setCurrentSolution(currentNode:Option[T], path:List[T], alreadyDoneNodes:Traversable[T]):Unit


}
