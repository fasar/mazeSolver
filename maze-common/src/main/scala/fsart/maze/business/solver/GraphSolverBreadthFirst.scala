package fsart.maze.business.solver

import fsart.maze.common.model._
import fsart.maze.business.{GraphSolver, GraphSolverPublisher}

import scala.collection.immutable.Queue
import scala.collection.immutable.HashMap


/**
 * Graph resolution with breadth first algorithm
 *
 * This is an iterative version.
 *
 * @author    fabien sartor
 * @version   1.0
 */
class GraphSolverBreadthFirst[T<: TraversableNode[T]] extends GraphSolver[T] {

  /**
   * @see fsart.maze.business.GraphSolver#solve(Maze, Floor, Floor)
   */
  def solve(start:T, end:T):List[T] = {
    var alreadyDone:List[T] = Nil
    var queue:Queue[T] = Queue[T](start)
    var reversePath:HashMap[T, T] = new HashMap[T, T]()
    var found: Boolean = false

    while(!this.stopSolve && queue.size!=0 && !found) {
      val tmp = queue.dequeue
      val curNode = tmp._1

      if(curNode == end) found=true
      alreadyDone = curNode::alreadyDone

      if(!found) {
        publish(Option(curNode), queue.toList, alreadyDone)

        val aroundNodes = curNode.aroundNodes
        var toExplore:List[T] = aroundNodes.filter (x => {!alreadyDone.contains(x) && !queue.contains(x) })
        toExplore foreach {
          x =>  reversePath = reversePath+(x->curNode)
        }
        queue = tmp._2 ++ toExplore
      }
    }

    if(found){
      var res:List[T] = Nil
      var orig:Option[T] = Option(end)
      while(orig.isDefined && orig.get!=start) {
        val node:T = orig.get
        orig = reversePath.get(node)
        res = node::res
      }

      if(orig!=None && orig.get==start) {
        publish(None, start::res, alreadyDone)
        start::res
      } else {
        Nil
      }
    } else {
      Nil
    }
  }


  /**
   * @see fsart.maze.business.GraphSolver#toString
   */
  override def toString():String = {
    "BreadthFirst"
  }

}
