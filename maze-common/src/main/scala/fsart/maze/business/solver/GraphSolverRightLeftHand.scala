package fsart.maze.business.solver

import fsart.maze.common.model._
import fsart.maze.business.{GraphSolver, GraphSolverPublisher}



/**
* Graph resolution with right or left hand algorithm
*
* This algo solves a graph in keeping the wall on the left (or right) hand.
* It doesn't work if there is an island or if there is no wall.
*
* This is an example of islands.
* This dots represent the loop of the algo in left hand
* {{{
* ##################################
* # . . . . . .  .             #   #
* # . ########## . ##########  # E #
* # . # ISLAND # S # ISLAND #  #   #
* # . ########## . ##########  #   #
* # . . . . . .  .                 #
* ##################################
* }}}
*
* This is an example of no wall.
* Dots represent an example of the loop of the algo in right hand.
* {{{
* #################
* #            E  #
* #               #
* #      S .      #
* #      . .      #
* #               #
* #################
* }}}
*
*
* @author    fabien sartor
* @version   2.0
*/
class GraphSolverRightLeftHand[T <: CardinalPointNode[T]] (leftHand:Boolean = true) extends GraphSolver[T] {
  import BodyDirection._

  /**
   * @see fsart.maze.business.GraphSolver#solve(Maze, Floor, Floor)
   */
  def solve(start:T, end:T):List[T] = {
    def solvR(start:T, end:T, direction:BodyDirection, path:List[T]): List[T] = {
      if(this.stopSolve) { return Nil }

      publish(Option(start), path, Nil)
      if(start == end) path
      else {
        val (nextCell:Option[T], d:BodyDirection) = getNextNode(start, direction)
          nextCell match {
            case Some(node:T) => solvR(node, end, d, start::path)
            case _ => Nil
          }
      }
    }

    /**
     * This function helps to get the next cell to visit.
     *
     * R is right, and L is left.
     *
     * To keep a wall at R side, you need to look if there is a wall at the R side.
     * If there is, all is all right, you take this way and the next cell you would
     * have a wall on you R side. Else you need to turn on your L until there is a
     * wall. When there is a Floor, you take this way.
     */
    def getNextNode(curNode:T, d:BodyDirection): (Option[T], BodyDirection) = {
      def turnnGoodDirection(d:BodyDirection): BodyDirection = {
        if(leftHand) { BodyDirection.turnRight(d) }
        else         { BodyDirection.turnLeft(d) }
      }
      def getNextNodeRec(curNode:T, d:BodyDirection, step:Int): (Option[T], BodyDirection) = {
        val nextNode:Option[T] = getFrontCell(curNode, d)
        nextNode match {
          case Some(node) => (nextNode, d)
          case None if step<4 => getNextNodeRec(curNode, turnnGoodDirection(d), step+1)
          case None => (None, d)
        }
      }

      if(leftHand) { getNextNodeRec(curNode, BodyDirection.turnLeft(d), 0) }
      else         { getNextNodeRec(curNode, BodyDirection.turnRight(d), 0)  }
    }

    /**
     * Get the cell in front of the body according the bodyDirection
     */
    def getFrontCell(curNode:T, bodyDirection:BodyDirection) : Option[T] = {
      bodyDirection match {
        case Weast => curNode.westNode
        case North => curNode.northNode
        case East =>  curNode.eastNode
        case South => curNode.southNode
      }
    }

    var res:List[T] = solvR(start, end, BodyDirection.North, Nil)
    return res.reverse
  }


   /**
   * @see fsart.maze.business.GraphSolver#toString
   */
  override def toString():String = {
    if(leftHand) "LeftHand"
    else "RightHand"
  }
}


/**
* This object is used to model directions.
* It is useful to turn right or left.
*/
object BodyDirection extends Enumeration {
  type BodyDirection = Value
  val Weast = Value("Weast")
  val East = Value("East")
  val North = Value("North")
  val South = Value("South")

  val rightTurn:Array[BodyDirection]= Array(North, East, South, Weast)

  /**
   * turn right from a direction
   */
  def turnRight(d:BodyDirection): BodyDirection = {
    rightTurn( (rightTurn.indexOf(d)+1) %  (rightTurn.size) )
  }

  /**
   * turn left from a direction
   */
  def turnLeft(d:BodyDirection): BodyDirection = {
    val index = rightTurn.indexOf(d)-1
    if(index<0)  rightTurn(rightTurn.size-1)
    else         rightTurn( index )
  }

}
