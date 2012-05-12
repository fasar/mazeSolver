package fsart.maze.business.solver

import fsart.maze.common.model._
import impl.TraversableCell
import impl.TraversableCell._
import org.scalatest.Suite

/**
*
* User: fabien
* Date: 15/08/11
* Time: 16:32
*
*/

class GraphSolverRightLeftHandTest  extends Suite {
  var maze:Maze = new DummyMaze()
  val start: TraversableCell = TraversableCell(maze, Floor(5,0))
  val end: TraversableCell = TraversableCell(maze, Floor(5,8))

  def testDirection() {
    assert(BodyDirection.turnRight(BodyDirection.North) == BodyDirection.East)
    assert(BodyDirection.turnRight(BodyDirection.East) == BodyDirection.South)
    assert(BodyDirection.turnRight(BodyDirection.South) == BodyDirection.Weast)
    assert(BodyDirection.turnRight(BodyDirection.Weast) == BodyDirection.North)
    assert(BodyDirection.turnLeft(BodyDirection.North) == BodyDirection.Weast)
    assert(BodyDirection.turnLeft(BodyDirection.East) == BodyDirection.North)
    assert(BodyDirection.turnLeft(BodyDirection.South) == BodyDirection.East)
    assert(BodyDirection.turnLeft(BodyDirection.Weast) == BodyDirection.South)
  }

  def testSolverLeftHand() {
    var solver = new GraphSolverRightLeftHand[TraversableCell](true)
    println("Test du solver version 2.0 : ")
    var response:List[Floor] = List(Floor(5,0), Floor(5,1), Floor(5,2), Floor(5,3), Floor(5,4), Floor(5,5), Floor(6,5), Floor(7,5), Floor(8,5), Floor(9,5), Floor(8,5), Floor(7,5), Floor(6,5), Floor(5,5), Floor(5,6), Floor(5,7))

    val resT:List[TraversableCell] = solver.solve(start, end)
    val res:List[Floor] = TraversableCell.getListNodes(resT)
    println("La réponse de lefthand est : " + res)
    assert(response==res, "answer is : " + res)
  }

  def testSolverRightHand() {
    var solver = new GraphSolverRightLeftHand[TraversableCell](false)
    var response:List[Floor] = List(Floor(5,0), Floor(5,1), Floor(5,2), Floor(5,3), Floor(5,4), Floor(5,5), Floor(4,5), Floor(3,5), Floor(2,5), Floor(1,5), Floor(0,5), Floor(1,5), Floor(2,5), Floor(3,5), Floor(4,5), Floor(5,5), Floor(5,6), Floor(5,7))

    val resT:List[TraversableCell] = solver.solve(start, end)
    val res:List[Floor] = TraversableCell.getListNodes(resT)
    println("La réponse de righthand est : " + res)
    assert(response==res, "answer is : " + res)
  }
}
