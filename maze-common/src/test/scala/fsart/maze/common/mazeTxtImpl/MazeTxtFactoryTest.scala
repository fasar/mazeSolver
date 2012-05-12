

package fsart.maze.common.mazeTxtImpl

import org.scalatest.Suite

import java.net.URL
import java.io.File
import fsart.maze.common.model.Maze


class MazeTxtFactoryTest extends Suite {
  var path:URL = this.getClass().getResource(".")
  var dir:File = new File(path.toURI)
  var mFact:MazeTxtFactoryImpl = null
  
  def reinit() {
    mFact = new MazeTxtFactoryImpl()
    assert(mFact.getMazeNames().size==0)
    mFact.addMazesFromDir(dir.toString())
    assert(mFact.getMazeNames().size!=0, "there is no maze in dir : " + dir.toString())
  }
  

  def testLoadMazeFileInDir() {
    reinit()
    assert(mFact.getMazeNames().contains("maze1.maze"), "There is no maze1.maze in dir : " + dir.toString() + ". Put a maze file in this dir")
  }

  def testFailLoadMazeFileInDir() {
    mFact = new MazeTxtFactoryImpl()
    assert(mFact.getMazeNames().size==0)
    mFact.addMazesFromDir("/notadirnotafiled")
    assert(mFact.getMazeNames().size==0)
  }

  def testFailGetMazeFromFile() {
    reinit()
    var maze:Option[Maze] = mFact.getMazeFromFile(new File("NoFile"))
    assert(maze==None)
  }


  def testGetMazeFromFile() {
    reinit()
    var maze:Option[Maze] = mFact.getMazeFromFile(new File("NoFile"))
    assert(maze==None)
  }

  def testGetMazeWithName() {
    reinit()
    var maze:Option[Maze] = mFact.getMaze("maze1.maze")
    assert(maze!=None, "can't get maze1.maze")
  }

  def testFailGetMazeWithName() {
    reinit()
    var maze:Option[Maze] = mFact.getMaze("NONAME")
    assert(maze==None)
  }
}
