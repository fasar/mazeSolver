package fsart.maze.business.solver

import fsart.maze.common.model._
import fsart.maze.common.model.impl._

import org.scalatest.Suite


class GraphSolverDepthFirstTest extends Suite {

  var maze:Maze = new DummyMaze()
  var solver = new GraphSolverDepthFirst[TraversableCell]()

  def testSolver() {
    val response:List[Floor] = Floor(5,0)::Floor(5,1)::Floor(5,2)::Floor(5,3)::Floor(5,4)::Floor(5,5)::Floor(5,6)::Floor(5,7)::Floor(5,8)::Nil
    val start: TraversableCell = TraversableCell(maze, Floor(5,0))
    val end: TraversableCell = TraversableCell(maze, Floor(5,8))
    println("Test du solver version 2.0 : ")
    val resT:List[TraversableCell] = solver.solve(start, end)
    val res:List[Floor] = TraversableCell.getListNodes(resT)
    println("La réponse donnée est : " + res)
    assert(res.size==response.size, "result size is not good : "+response.size+"!=" + res.size + "\nRes is : " + res)
    assert( res == response )
  }

  def testFailSolver() {
    val start: TraversableCell = TraversableCell(maze, Floor(5,0))
    val end: TraversableCell = TraversableCell(maze, Floor(0,0))
    val resT:List[TraversableCell] = solver.solve(start, end)
    assert(resT.size==0, "result size is not good. 0 != " + resT.size)
  }

}






