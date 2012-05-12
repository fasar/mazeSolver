package fsart.maze.app.gui

import fsart.maze.common.model._
import fsart.maze.business.{GraphSolver}
import impl.TraversableCell


/**
 *  Thread wrapper to process algo.
 * 
 *  @author     fabien sartor
 *  @version    1.0
 */
class solverThread(val solver:GraphSolver[TraversableCell], maze:Maze) extends Thread {

  override def run() {
    for(start<-maze.start; end<-maze.end) {
      val startNode:TraversableCell = TraversableCell(maze, start)
      val endNode:TraversableCell = TraversableCell(maze, end)
      solver.solve(startNode, endNode)
    }
  }

  def stopThread() {
    solver.stopSolve = true
  }

}
