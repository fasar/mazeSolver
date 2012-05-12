
package fsart.maze.common.mazeTxtImpl

import org.scalatest.Suite

import fsart.maze.common.mazeTxtImpl._

import java.net.URL
import java.net.URI
import java.io.File
import java.io.IOException
import java.io.InputStream


class TxtToStrListTest extends Suite {
  
  def testAGetRessource() {
    var path:URL = this.getClass().getResource(".")
    var url:URL = this.getClass().getResource("./getResource")
    assert(url != null, "I can't get \"getRessources.txt\" at " + path)
  }

  def testReadFile() {
    var url:URL = this.getClass().getResource("./getResource")
    var fic:File = new File(url.toURI)
    assert(fic.isFile(), "File "+ url.toString() +" isn't a file, please put a txt file at " + fic.getCanonicalPath)
    var ips:InputStream = url.openStream()
    var c:Int = ips.read()
    assert(c>0, "file doesn't contain data : " + fic.getCanonicalPath)
  }
  
  def testConvert() {
    var url:URL = this.getClass().getResource("./getResource")
    var fic:File = new File(url.toURI)
    var list:List[String] = TxtToStrList.convert(fic)
    assert(list!=Nil, "file doesn't contain data : " + fic.getCanonicalPath)
  }

  def testFailFileConvert() {
    var fic:File = new File("nonfile.txt")
    var list:List[String] = TxtToStrList.convert(fic)
    assert(list==Nil, "file contains data : " + fic.getCanonicalPath)
  }

  
}
