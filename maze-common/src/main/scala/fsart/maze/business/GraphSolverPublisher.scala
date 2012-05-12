package fsart.maze.business

import fsart.maze.common.model._

/**
 * This trait is an abstract class for solvers
 *
 * An object with this abstract class informs its listeners each step of resolution.
 * To do that, it uses the publish function.
 *
 * GraphSolverListener objects need to be registered with addListener.
 *
 *  @author   fabien sartor
 *  @version  1.0
 */
trait GraphSolverPublisher[N] {
  private[this] var listeners:List[GraphSolverListener[N]] = Nil

  /**
   * function to register listener
   */
  def addListener(x:GraphSolverListener[N]) {
    listeners = x::listeners
  }

  /**
   * Function to del a listener
   */
  def delListener(x:GraphSolverListener[N]) {
    listeners = listeners filterNot(_ == x)
  }

  /**
   * Function used by the publisher object to inform listeners a step is done.
   */
  protected def publish(curCell:Option[N], path:List[N], alreadyDoneCells:Traversable[N]) {
    listeners foreach {
      x => x.setCurrentSolution(curCell, path, alreadyDoneCells)
    }
  }
}
