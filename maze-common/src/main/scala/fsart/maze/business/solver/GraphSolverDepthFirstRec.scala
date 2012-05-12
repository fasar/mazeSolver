package fsart.maze.business.solver

import fsart.maze.common.model._
import fsart.maze.business.{GraphSolver, GraphSolverPublisher}
import scala.annotation.tailrec
import collection.mutable.HashSet


/**
 * Graph resolution with detph first algorithm.
 * This class use the recursive solution.
 *
 * @author    fabien sartor
 * @version   2.0
 */
class GraphSolverDepthFirstRec[T <: TraversableNode[T]] extends GraphSolver[T] {

  /**
   * @see fsart.maze.business.GraphSolver#solve(Maze, Floor, Floor)
   */

  def solve(start:T, end:T): List[T] = {

    def solveR(curNode:T,alreadyDone:HashSet[T], path:List[T]): (HashSet[T], List[T]) = {

      @tailrec
      def exploreNeighbour(toExplore:List[T], alreadyDone2:HashSet[T]): (HashSet[T], List[T]) = {
        toExplore match {
          case Nil =>
            (alreadyDone2, Nil)
          case x::xs =>
            solveR(x, alreadyDone2 + x, x::path) match {
              case (nodeDone, Nil) => exploreNeighbour(xs, alreadyDone2 + x)
              case otherRes => otherRes
          }
        }
      }

      publish(Option(curNode), path, alreadyDone)
      if( this.stopSolve ) {
        (alreadyDone, Nil)
      } else {
        if( curNode==end) { (alreadyDone, path) }
        else {
          val toExplore:List[T] = curNode.aroundNodes filterNot { alreadyDone.contains(_) }
          exploreNeighbour(toExplore, alreadyDone + curNode)
        }
      }
    }

    var res = solveR(start, HashSet(start), start::Nil)
    res._2.reverse
  }

  /**
   * @see fsart.maze.business.GraphSolver#toString
   */
  override def toString():String = {
    "DepthFirstRec"
  }
}
