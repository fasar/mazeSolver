package fsart.maze.business

import fsart.maze.business.solver._
import fsart.maze.common.model._

//TODO: This version is very basic, it can be refactored with introspection in fsart.maze.business.solver package

/**
 * Factory of graph solver
 *
 *
 * @author    fabien sartor
 * @version   2.0
 */
class GraphSolverFactory[T <: TraversableNode[T] with CardinalPointNode[T]] {

  var solvers:List[String] = "DepthFirst"::"DepthFirstRec"::"BreadthFirst"::"LeftHand"::"RightHand"::"AStar"::Nil

  /**
   * Get the list of solvers can be used to solve a maze
   */
  def getSolver():List[String] = {
    solvers
  }

  /**
   * Get a solver with it name
   */
  def getSolver(name:String): Option[GraphSolver[T]] = {
    name match {
      case "DepthFirst" => Some(getDepthFirstSolver())
      case "DepthFirstRec" => Some(getDepthFirstSolverRec())
      case "BreadthFirst" => Some(getBreadthFirstSolver())
      case "LeftHand" => Some(getLeftHandSolver())
      case "RightHand" => Some(getRightHandSolver())
      case "AStar" => Some(getAStarSolver())
      case _ => None
    }
  }

  /**
   * Get a DepthFirstSolver object
   */
  private def  getDepthFirstSolver(): GraphSolverDepthFirst[T] = {
    return new GraphSolverDepthFirst[T]()
  }

  /**
   * Get a DepthFirstSolverRec object
   */
  private def  getDepthFirstSolverRec(): GraphSolverDepthFirstRec[T] = {
    return new GraphSolverDepthFirstRec[T]()
  }

  /**
   * Get a BreadthFirstSolver object
   */
  private def getBreadthFirstSolver(): GraphSolverBreadthFirst[T] = {
    return new GraphSolverBreadthFirst[T]()
  }

  /**
   * Get a LeftHandSolver object
   */
  private def getLeftHandSolver(): GraphSolverRightLeftHand[T] = {
    return new GraphSolverRightLeftHand[T](true)
  }

  /**
   * Get a RightHandSolver object
   */
  private def getRightHandSolver(): GraphSolverRightLeftHand[T] = {
    return new GraphSolverRightLeftHand[T](false)
  }

  /**
   * Get a AStarSolver object
   */
  private def getAStarSolver(): GraphSolverAStar[T] = {
    return new GraphSolverAStar[T]()
  }
}
