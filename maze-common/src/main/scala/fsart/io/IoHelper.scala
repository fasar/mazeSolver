package fsart.io

import java.io.File
import java.net.URL


/**
 * This is a helper module for io functions.
 *
 */
object IoHelper {

  /**
   * Helper function to get an URL object from a file
   */
  def getUrlFile(f: File): Option[URL] = {
    if(f==null) return None
    try {
      val url:URL = f.toURI().toURL();
      Some(url)
    } catch {
      case e => e.printStackTrace();
      None
    }
  }

   /**
    * This helper function return an URL object from a file.
    *
    * The path can be in the ClassPath of the Classloader (event
    * in the jar) or it can be an absolute pathname.
    * This function seek in classpath first.
    */
   def getUrlPath(path: String): Option[URL] = {
     if(path==null) return None
     try {
       Option(this.getClass.getResource(path)) orElse {
        val fic = (new File(path))
        if(fic.exists()) Option(fic.toURI.toURL)
        else None
       }
     } catch {
       case e => None
     }
   }

}
