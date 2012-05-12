package fsart.maze.app.gui

import guiComponents.mazePanel
import scala.swing._
import scala.swing.event._
import javax.swing.Timer
import fsart.maze.common.model._
import fsart.maze.common.model.impl._
import fsart.maze.app.gui.guiComponents._
import fsart.maze.app.gui._
import fsart.maze.business.GraphSolverListener

/**
 * Main windows singleton of the gui application.
 *
 * This singleton is a container for the top main frame.
 *
 * This singleton creates the view and implements a function to
 * quit the windows application.
 * It needs a swing thread to run.
 * @see #startup
 *
 *
 *
 * @author    fabien sartor
 * @version   1.0
 *
 */
object MasterWindows extends AnyRef with GraphSolverListener[TraversableCell] with Reactor { //extends SimpleSwingApplication  {
  var timeToWait:Int = 650

  private lazy val mazePanelWrap = new ScrollPane(mazePanel)
  private lazy val mazePanelWrap2 = new ScrollPane(selectorPanel) {this.preferredSize = new Dimension(200, 600)}
  private lazy val myPane = new BorderPanel {
    add(mazePanelWrap, BorderPanel.Position.Center)
    add(mazePanelWrap2, BorderPanel.Position.East)
    add(ctrlPanel, BorderPanel.Position.South)
  }

  /**
   * This is the main container of the windows
   */
  var top = new MainFrame {
    title = "Maze Solver"
    preferredSize = new Dimension(800, 600)
    contents = myPane
    reactions += {
      case WindowClosing(src) => quit()
    }
  }




  /**
   *  Startup function of the gui.
   *
   *  It must be a Swing Thread.
   *  Use the Swing singleton to start the windows application.
   *  Ex: Swing.onEDT( MainGui.startup(args) )
   */
  def startup(args: Array[String]) {
    val t = top
    if (t.size == new Dimension(0,0)) t.pack()
    t.visible = true
  }

  /**
   * Quit the application.
   *
   * Stop thread(s) and exit.
   */
  def quit() {
    guiControler.stopCurrentThreadSolver()
    top.dispose()
    //System.exit(0)
  }


  /**
   * @see GraphSolverListener#setCurrentSolution
   */
  def setCurrentSolution(currentNode:Option[TraversableCell], path:List[TraversableCell], alreadyDoneNodes:Traversable[TraversableCell]) {
    val path2:List[Cell] = path  map { _.node }
    val alreadyDoneCells:List[Cell] = alreadyDoneNodes.toList map { _.node }
    val currentCell: Option[Cell] = currentNode map { _.node }
    mazePanel.setCurrentSolution(currentCell, path2, alreadyDoneCells)
    Thread.sleep(timeToWait)
  }

}







