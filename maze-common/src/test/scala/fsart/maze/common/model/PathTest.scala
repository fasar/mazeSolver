package fsart.maze.common.model


import org.scalatest.Suite

import scala.collection.immutable.HashMap



class PathTest extends Suite {

  def addPath(path:HashMap[Floor, Floor], l:List[Floor]):HashMap[Floor, Floor] = {
    l match {
      case x1::x2::t => addPath( path.updated(x1, x2), x2::t)
      case _ => path
    }
  }


  def testPath() {
    var path:HashMap[Floor, Floor] = new HashMap[Floor, Floor]()
    var l:List[Floor] = (Floor(1,1))::(Floor(1,2))::(Floor(2,2))::(Floor(2,3))::Nil
    path = addPath(path, l)
  }
}
