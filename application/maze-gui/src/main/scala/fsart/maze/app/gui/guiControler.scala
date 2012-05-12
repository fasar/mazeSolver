package fsart.maze.app.gui

import fsart.maze.app.gui.guiComponents._
import fsart.maze.common.MazeFactory
import fsart.maze.common.model.Maze
import java.awt.event.{ActionEvent, ActionListener}
import swing.UIElement._
import fsart.maze.common.model.impl.TraversableCell
import fsart.maze.business.{GraphSolverFactory, GraphSolver}


/**
 *  This is the controler of the view.
 *
 *  It manages the gui.
 *
 * @author  fabien sartor
 * @version 1.0
 *
 */
object guiControler {

  private var curMaze:Option[Maze] = None
  private var curSolver:Option[GraphSolver[TraversableCell]] = None
  private var curThreadSolver:solverThread = _

  private var mazeSolverFactory:GraphSolverFactory[TraversableCell] = _
  private var mazeFactory:MazeFactory = _



  /**
   * Set the maze factory
   *
   * It displays all the maze from the maze factory.
   *
   * @param mazeFactory   an implementation of a maze factory
   *                      @see fsart.maze.business.GraphSolver
   */
  def setMazeFactory(mazeFactory:MazeFactory) {
    this.mazeFactory = mazeFactory
    setListMazes(mazeFactory.getMazeNames())
  }

  /**
   * Set the swing list of mazes
   */
  private def setListMazes(mazes: List[String]) {
    selectorPanel.listMaze.listData = mazes
  }

  /**
   * Define the new maze to process
   *
   */
  def defCurrentMaze(name: String) {
    if(name!=curMaze) {
      curMaze = mazeFactory.getMaze(name)
      if(curMaze.isDefined) {
        setMaze(curMaze.get)
      }
    }
  }

  /**
   * Define the new maze to process
   *
   */
  def setMaze(maze:Maze) {
    stopCurrentThreadSolver()
    mazePanel.setMaze(maze)
    mazePanel.repaint()
  }


   /**
   * Set the maze solver factory.
   *
   * It displays solvers algorithms form the solver factory.
   *
   * @param mazeSolverFactory   an implementation of a maze solver factory.
   *                            @see fsart.maze.business.GraphSolverFactory
   *
   */
  def setMazeSolverFactory(mazeSolverFactory:GraphSolverFactory[TraversableCell]) {
    this.mazeSolverFactory = mazeSolverFactory
    setListAlgos(mazeSolverFactory.getSolver())
  }

  /**
   * Set the list of algo
   *
   */
  def setListAlgos(algosName: List[String]) {
    selectorPanel.listAlgo.listData = algosName
  }

  /**
   * Define the algo to use to proccess the current maze.
   *
   */
  def defCurrentAlgo(algoName: String) {
    curSolver = mazeSolverFactory.getSolver(algoName)
    curSolver match {
      case Some(solver) =>
        stopCurrentThreadSolver()
        solver.addListener(MasterWindows)
        curThreadSolver = new solverThread(solver, curMaze.get)
        curThreadSolver.start()
      case None =>
    }
  }


  /**
   * Stop currents thread and wait they finish
   */
  def stopCurrentThreadSolver() {
    if(curThreadSolver!=null && curThreadSolver.isAlive) {
      curThreadSolver.stopThread()
      curThreadSolver.solver.delListener(MasterWindows)
    }
  }

}

