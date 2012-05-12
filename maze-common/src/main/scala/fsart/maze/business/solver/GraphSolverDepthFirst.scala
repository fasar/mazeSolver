package fsart.maze.business.solver

import fsart.maze.common.model._
import fsart.maze.business.{GraphSolver, GraphSolverPublisher}
import scala.annotation.tailrec


/**
 * Graph resolution with detph first algorithm
 *
 * It use a trick to be tailrec. Each cells it need to explore it
 * put its in a list todoNodes. Before put these nodes in the list
 * it put a dropper.
 *
 * A dropper is a marker. When you read it in the todoNodes you
 * must understand : we need to go back in history.
 * This last thing is done with dropping the last element put in  the
 * path.
 *
 * In this algo, the dropper is represented as the Nil list in todoNodes.
 * Each item of TodoNodes is a group of direction choise the algo can do.
 *
 * @author    fabien sartor
 * @version   2.0
 */
class GraphSolverDepthFirst[T <: TraversableNode[T]] extends GraphSolver[T] {

  /**
   * @see fsart.maze.business.GraphSolver#solve(Maze, Floor, Floor)
   */
  def solve (start:T, end:T): List[T] = {
    @tailrec
    def solveR(todoNode:List[List[T]], alreadyDone:List[T], path:List[T]): (List[T], List[T]) = {
      if( this.stopSolve ) {
        (Nil, Nil)
      } else {
        todoNode match {
          case Nil::xs =>
            val newPath = path.drop(1)
            publish(None, newPath, alreadyDone)
            solveR(xs, alreadyDone, newPath )
          case ((curNode)::xs)::_ if curNode == end =>
            publish(Option(curNode), path, alreadyDone)
            (alreadyDone, curNode::path)
          case ((curNode)::xs)::todoTail =>
            publish(Option(curNode), path, alreadyDone)
            val toExplore:List[T] =
                ( curNode.aroundNodes filter( x=>{!alreadyDone.contains(x)} ) )
            solveR( toExplore::xs::todoTail, curNode::alreadyDone, curNode::path )
          case Nil =>  (Nil, Nil)
        }
      }

    }

    val res = solveR(List(start::Nil), Nil, Nil)
    res._2.asInstanceOf[List[T]].reverse
  }


  /**
   * @see fsart.maze.business.GraphSolver#toString
   */
  override def toString():String = {
    "DepthFirst"
  }
}
