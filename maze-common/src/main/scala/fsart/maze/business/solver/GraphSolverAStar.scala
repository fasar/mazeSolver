package fsart.maze.business.solver

import fsart.maze.business.{GraphSolverPublisher, GraphSolver}
import fsart.maze.common.model._

import scala.collection.immutable.TreeSet
import scala.collection.mutable.HashSet



/**
 * Graph resolution with AStar algorithm.
 *
 * source : http://jaguilar.posterous.com/a-star-search-in-scala-part-two
 *
 * @version   2.0
 * @author    Slow James
 *
 *
 */
class GraphSolverAStar[T<: TraversableNode[T]] extends GraphSolver[T] {

  /**
   * Models a tree of a path and the destination.
   *
   * This is a recursive structure. It is useful to know cost of the
   * path from starting node to the current node. It is useful to forecast
   * the cost to go to the destination.
   */
  class Node(val node: T, val from: Node, val dest: Node) {
    /** Actual cost up to this point. */
    val actual: Int = if (from == null) 0 else from.actual + cost

    /** Estimate of the cost from here to the goal. */
    val estimate = if (dest == null) 0 else actual + heuristic(dest.node)

    /** Return a list of nodes that are adjacent to the current node.*/
    def adjacent =  {
      val aroundNodes: List[T] = node.aroundNodes
      aroundNodes map { s => new Node(s, this, dest) }
    }

    override def hashCode = node.hashCode

    override def equals(other: Any) = node.equals(other.asInstanceOf[Node].node)

    override def toString() = node.toString

    /** Get the path from the start to this node. */
    def toPath = toPathHelp.reverse

    /** Yields a list of the path up to this point, in reverse. */
    def toPathHelp: List[T] = if (from != null) node :: from.toPathHelp else List(node)

    /** The cost of a cell. */
    def cost:Int = {
      1
    }

    /** The heuristic distance between this cell and another.
    *  Must be admissible in the sense of a* search.
    */
    def heuristic(other: T): Int =  node.estimateWeightToGoTo(other).toInt

  }

  /**
   *  Priority Queue with update function
   */
  class EditablePriorityQueue {
    var open = new TreeSet[Node]()(Ordering.by((n: Node) => (n.estimate, n.hashCode)))
    val openSet = new HashSet[Node]
    def push(n: Node) = {
      openSet.add(n)
      open = open + n
    }

    def pop(): Node = {
      val n = open.head
      remove(n)
      return n
    }

    def nonEmpty() = open.nonEmpty
    def remove(n: Node) = {
      openSet.removeEntry(n)
      open = open - n
    }

    def update(n: Node): Boolean = {
      val incumbent = openSet.findEntry(n).getOrElse(null)
      if (incumbent == null) return false
      if (incumbent.actual > n.actual) {
        remove(incumbent)
        push(n)
      }
      return true
    }

    def toList():List[Node] = {
      openSet.toList
    }
  }


  /**
   * @see fsart.maze.business.GraphSolver#solve(Maze, Floor, Floor)
   */
  def solve(start:T, end:T): List[T] = {
    val closed = new HashSet[Node]
    val open = new EditablePriorityQueue()
    open.push(new Node(start, null, new Node(end, null, null)))
    while (open.nonEmpty && ! this.stopSolve ) {
      val elem = open.pop()
      publishHelper(elem.node, elem.toPath, closed.toList)
      if (elem.node == end) {
        // If we've reached the goal node . . .
        return elem.toPath
      }
      closed.add(elem)
      // Iterate over the neighbors that are not in the closed set.
      for (n <- elem.adjacent.filter(n => !closed.contains(n))) {
        // Replace the incumbent if n is better.
        if (!open.update(n)) {
          // If no incumbent exists, simply add n.
          open.push(n)
        }
      }
    }

    return Nil
  }


  /**
   * help to translate type of variable of this algo to the correspondent
   * type of publish function in GraphSolverPublisher
   */
  private def publishHelper(node:T, path:List[T], nodes:List[Node]) {
    val alreadyD:List[T] = nodes collect {case x:Node => x.node}
    publish(Some(node), path, alreadyD)
  }


   /**
   * @see fsart.maze.business.GraphSolver#toString
   */
  override def toString():String = {
    "AStar"
  }
}
