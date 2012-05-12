package fsart.maze.business


import fsart.maze.common.model.TraversableNode

/**
 * Interface for a graph solver
 *
 * A graph solver is an element taht seeks a node from a starting node.
 * It returns the list of traversed node to go to the final node.
 *
 * @author    fabien sartor
 * @version   2.0
 */
abstract class GraphSolver[T] extends GraphSolverPublisher[T] {

  var stopSolve:Boolean = false

  /**
   * Solve a maze with the underlying algorithm of the object.
   */
  def solve(start:T, end:T): List[T]


  /**
   * This function gives the name of the underlying algorithm
   */
  override def toString():String
}
