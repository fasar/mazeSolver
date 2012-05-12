package fsart.maze.app.bmpExtractor



import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream
import javax.imageio.metadata._
import java.awt.image.BufferedImage
import java.util.Iterator;
import fsart.maze.common.model._
import fsart.maze.common.mazeTxtImpl._

import fsart.io.IoHelper
import java.net.URL
import java.io.{File, InputStream}
;

object Main {
  val listOptions:List[String] = "-h"::"-m"::"-g"::Nil

  def main(args: Array[String]) {

    val nbArgs = args.size
    if(nbArgs==0 || args.contains("-h"))
      usage(args, nbArgs==0)

    val affMetaData:Boolean = args contains "-m"
    val affGui:Boolean = args contains "-g"

    val files:Array[String] = args filterNot { listOptions contains _  }

    //val operation:Function1[String, Unit] = {
    val operation:((URL,ImageReader) => Unit) = {
      if(affMetaData) {
        printMetaData(_, _)
      } else if(affGui) {
        convertWithGui(_, _)
      } else {
        convertWithoutGui(_, _)
      }
    }


    for( arg <- files; url <- IoHelper.getUrlPath(arg) ){
      try {
        val path:String = url.toString
        println("Convert the file : " + path)

        //File reader
        val source:InputStream = url.openStream()
        val iis:ImageInputStream = ImageIO.createImageInputStream(source)

        //Image reader init with the extension of the file
        val extensionName:String = path.drop(path.lastIndexOf('.') + 1)
        val readers:Iterator[ImageReader] = ImageIO.getImageReadersBySuffix(extensionName)
        val imageReader:ImageReader =  readers.next()
        imageReader.setInput(iis, true)

        val fileName:String = path.dropRight(path.size - path.lastIndexOf('.') ).drop(path.lastIndexOf(java.io.File.separatorChar) + 1)

        operation(url, imageReader)
        //convertWithGui(url.getFile, imageReader)

        //close the opened file
        iis.close()
        source.close()
      } catch {
        case ex =>
          println("Can't convert : " + arg)
          println("Error is : ")
          ex.printStackTrace()
      }
    }
  }

  def usage(args:Array[String], error:Boolean) {
    println("Usage: mextract [option] mazefile1 mazefile2 ...")
    println("")
    println("   -g         convert maze and display a gui to show some information")
    println("   -h         this help message")
    println("   -m         metadata details of files mazefileX")
    System.exit(if(error) 1 else 0)
  }

  /**
   * Read the meta data of the image
   */
  def printMetaData(file:URL, imageReader:ImageReader) {
    println("MetaData of :" + file.getPath)
    println("Size is of the picture is : " + imageReader.getWidth(0) + "x" + imageReader.getHeight(0))
    val metadata:IIOMetadata = imageReader.getImageMetadata(0)
    val names: Array[String] = metadata.getMetadataFormatNames()
    for (name <- names) {
        System.out.println( "Driver name : " + name)
        ImageTools.displayMetadata(metadata.getAsTree(name))
    }
  }


  def convertWithGui(file:URL, imageReader:ImageReader) {
    // Get the image and show it
    val bimage:BufferedImage =  imageReader.read(0)
    val greyImage:BufferedImage = ImageTools.toGrey(bimage)
    val gui:ImageViewer = ImageViewer(bimage)
    // Show some quick caracteristics like numbers of black pixels in column or line
    gui.columnSig = ImageCharacteristics.getSigImage(ImageCharacteristics.getColumnSig(greyImage), 100)
    val lineSigImg:BufferedImage = ImageCharacteristics.getSigImage(ImageCharacteristics.getLineSig(greyImage), 100)
    gui.lineSig = ImageTools.rotate(lineSigImg, -90)
    // Convert image with canny filter to get edge. It can fail if color is not well defined
    try {
      gui displayImage Converter.getEdgedImage(bimage)
    } catch { case ex => "" }

    gui displayImage Converter.getImageWithBorder(greyImage)

    // Convert image in maze
    val convMaze:Maze = convert(file, bimage)
    mazePanel2.setMaze(convMaze)
    //Show maze
    gui addPanel mazePanel2
  }

  def convertWithoutGui(file:URL, imageReader:ImageReader) {
    val bimage:BufferedImage =  imageReader.read(0)
    convert(file, bimage)
  }

  def convert(file:URL, bimage:BufferedImage):Maze = {
    //get the image
    val greyImage:BufferedImage = ImageTools.toGrey(bimage)
    // process it
    val convMaze:Maze = Converter.convert(greyImage)
    // Extraction du fichier txt
    val path:String = file.getFile
    val fileName:String = path.dropRight(path.size - path.lastIndexOf('.') ).drop(path.lastIndexOf(java.io.File.separatorChar) + 1)
    val fic:File = new File(file.getFile)
    val pathDirName:String =
      if(fic.isFile) { path.dropRight(path.size - path.lastIndexOf(java.io.File.separatorChar)) }
      else { System.getProperty("user.dir") }

    val outputPath:String = pathDirName + java.io.File.separator + fileName + ".maze"

    MazeTxtSav.saveToFile( outputPath , convMaze)
    println("Conversion done.\n" +
      "Maze file is : " + outputPath)
    convMaze
  }
}

