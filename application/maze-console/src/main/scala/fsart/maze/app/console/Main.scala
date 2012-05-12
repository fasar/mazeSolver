package fsart.maze.app.console

import fsart.maze.common.model._
import impl.TraversableCell
import java.io.File
import fsart.maze.common.mazeTxtImpl.{MazeTxtFactoryImpl}
import fsart.maze.business.{GraphSolver, GraphSolverFactory}

/**
 * Console application of maze solver
 *
 *  @author     fabien sartor
 *  @version    1.0
 */
object Main {
  val solverFactory:GraphSolverFactory[TraversableCell] = new GraphSolverFactory[TraversableCell]()
  val mazeFactory:MazeTxtFactoryImpl = new MazeTxtFactoryImpl()
  var curMaze:Option[Maze] = None


  /**
   * Entry point
   */
  def main(args: Array[String]) {
    val nbArgs = args.size
    if(nbArgs==0)
      usage(args, true)
    args foreach {
      case "-l" => printAlgos() ; System.exit(0)
      case "-h" => usage(args, false) ; System.exit(0)
      case _ =>
    }

    curMaze = getMaze(args)
    if(curMaze.isEmpty) {
      println("Can't load Maze");
      System.exit(1)
    }

    var curSolver:GraphSolver[TraversableCell] = getSolver(args)
    val maze = curMaze.get
    if(maze.start.isEmpty || maze.end.isEmpty) {
      println("Can't find start or stop position in maze file")
      System.exit(1)
    }
    val start:TraversableCell = TraversableCell(maze,maze.start.get)
    val end:TraversableCell = TraversableCell(maze, maze.end.get)
    val resT:List[TraversableCell] = curSolver.solve(start, end)
    val res:List[Floor] = TraversableCell.getListNodes(resT)

    println("Conversion with " + curSolver.toString + " algo.")
    val rep:String =
      if(res.isEmpty)
        "There is no solution"
      else
        res.mkString("Response is : ", ",", ".")
    println(rep)

    System.exit(0)
  }

  /**
   * Usage of the application and exit.
   */
  def usage(args: Array[String], error:Boolean) {
    println("Usage: msolver -f mazefile [-a algo]")
    println("       msolver -l")
    println("")
    println("   -a algo    use the algo")
    println("   -f path    maze file")
    println("   -h         this help message")
    println("   -l         list all algo you can use")
    System.exit(if(error) 1 else 0)
  }

  /**
   * print what kind of algo you can use
   */
  def printAlgos() {
    println("Algo you can use are : ")
    println(solverFactory.getSolver().mkString("         ", ", ", "."))
  }

  /**
   * get a maze from a file
   */
  def getMaze(args: Array[String]):Option[Maze] = {
    var curIndex: Int = 0
    var maze:Option[Maze] = None
    curIndex = args.indexOf("-f")
    curIndex+=1
    if(curIndex<=0 || curIndex>= args.size) {
      println("file not found after -f option");
      usage(args, true)
    }

    maze = mazeFactory.getMazeFromFile(new File(args(curIndex)))
    if(maze.isEmpty) {
      println("File doesn't exist : " + args(curIndex) );
      System.exit(1)
    }
    return maze
  }

  /**
   * Get the algo wanted algo
   */
  def getSolver(args: Array[String]):GraphSolver[TraversableCell] = {
    var curIndex: Int = 0
    var res:Option[GraphSolver[TraversableCell]] = None

    var listAlgo = solverFactory.getSolver()
    if(listAlgo.size <= 0) {
      println("There is no algo")
      System.exit(1)
    }

    curIndex = args.indexOf("-a")
    var nextIndex:Int = curIndex+1
    if(curIndex>=0 && nextIndex < args.size) {
      res = solverFactory.getSolver(args(nextIndex))
    }
    if(res.isEmpty) {
      println("by defaut algo is use : " + listAlgo(0));
      res = solverFactory.getSolver(listAlgo(0))
    }
    if(res.isEmpty) {
      println("Can't load by default algo")
      System.exit(1)
    }

    return res.get
  }
}
