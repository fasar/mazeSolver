
package fsart.maze.common.mazeTxtImpl

import java.io.File;
import scala.io.{Source, BufferedSource}

/**
 * Helper module to extract line of file
 *
 * @author    fabien sartor
 * @version   1.0
 *
 */
object TxtToStrList {
    def convert(fic:File):List[String] = {
    try {
      val res:BufferedSource = Source.fromFile(fic)
      res.reset.getLines().toList
    } catch {
      case e:Exception => Nil
    }
  }
}
