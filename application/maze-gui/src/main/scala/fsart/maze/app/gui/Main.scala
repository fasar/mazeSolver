package fsart.maze.app.gui

import fsart.maze.common.mazeTxtImpl.MazeTxtFactoryImpl
import fsart.maze.common.model.impl._
import scala.swing.Swing
import fsart.maze.business.GraphSolverFactory
import fsart.io.IoHelper


/**
 *  Entry point of the gui application.
 *
 *  It handles the command args
 *
 *  @author     fabien sartor
 *  @version    1.0
 */
object Main {
  private val mazeSolverFactory = new GraphSolverFactory[TraversableCell]()
  private val mazeFactory = new MazeTxtFactoryImpl()

  /**
   * Entry point
   */
   def main(args: Array[String]) {

     val mazeArgs:Array[String] = if(args.size >= 1) args else Array(java.io.File.separator)

     // Load maze dirs in the container mazeFactory
     for( arg <- mazeArgs;
          urlMaze <- IoHelper.getUrlPath(arg))
          { mazeFactory.addMazesFromDir(urlMaze.getPath) }

     // Link the application
     guiControler.setMazeFactory(mazeFactory)
     guiControler.setMazeSolverFactory(mazeSolverFactory)

     // Start the swing thread.
     Swing.onEDT( MasterWindows.startup(args) )
   }


}
