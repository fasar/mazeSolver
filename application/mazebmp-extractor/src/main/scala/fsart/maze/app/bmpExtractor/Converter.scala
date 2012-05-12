package fsart.maze.app.bmpExtractor

import annotation.tailrec
import java.awt.image.{WritableRaster, Raster, BufferedImage}
import java.awt.{Color, Graphics2D}
import fsart.maze.common.model._
import fsart.maze.imagetools.CannyEdgeDetector

object Converter {
  import fsart.maze.app.bmpExtractor.ImageTools._


  /**
   * Use a Canny filter to get a new image with the edge
   */
  def getEdgedImage(img:BufferedImage):BufferedImage = {
     //create the detector
     val detector:CannyEdgeDetector = new CannyEdgeDetector();

     //adjust its parameters as desired detector.setLowThreshold(0.5f);
     detector.setHighThreshold(1f);

     //apply it to an image
     detector.setSourceImage(img);
     detector.process();
     detector.getEdgesImage();
  }

  /**
   * Add a red grid to each detected edge.
   */
  def getImageWithBorder(img:BufferedImage):BufferedImage = {
    //Create a copy of img
    val workImg:BufferedImage = toRVB(ImageTools.cloneImage(img))
    // Get the list of edges
    val edgeH:List[Int] =  EdgeDectector.getIndexesChangeHorizontal(workImg, 1.0)
    val edgeV:List[Int] =  EdgeDectector.getIndexesChangeVertical(workImg, 1.0)

    val g:Graphics2D = workImg.createGraphics
    g.setColor(Color.RED)
    //Draw vertical line
    for(verticalLinePos <- edgeH) {
      g.drawLine(verticalLinePos, 0, verticalLinePos, workImg.getHeight-1)
    }
    //Draw horizontal line
    for(horizontalLinePos <- edgeV) {
      g.drawLine(0, horizontalLinePos, workImg.getWidth-1, horizontalLinePos)
    }

    g.dispose()
    workImg
  }

  /**
   * Convert a Grey image in Maze.
   *
   * The param img need to be a image with grey level.
   * You can use ImageTools.toGrey to get a grey image
   */
  def convert(img:BufferedImage):Maze = {
    //Get the cell (Wall or Floor) function of the color of the pixel
    def getCellWithCoord(v:Int ,h:Int, x:Int, y:Int):Cell = {
      val pixColor:Array[Int] = img.getRaster.getPixel(v,h,null)
      if ( 127>pixColor(0)) Wall(y, x)
      else Floor(y,x)
    }

    val edgeH:List[Int] =  EdgeDectector.getIndexesChangeHorizontal(img, 1.0)
    val edgeV:List[Int] =  EdgeDectector.getIndexesChangeVertical(img, 1.0)

    val cells:Seq[Array[Cell]] = for((v, y) <- edgeH.zipWithIndex) yield  {
      val lineCell:Seq[Cell] = for((h, x) <- edgeV.zipWithIndex) yield {
        getCellWithCoord(v ,h, x, y)
      }
      lineCell.toArray
    }

    new Maze(cells.toArray)
  }



  object EdgeDectector {
     /**
     * Get list of index when the pattern change in vertical scanning (from up to down)
     */
    def getIndexesChangeVertical(img:BufferedImage, threashold:Double):List[Int] = {
      val raster:Raster = img.getRaster
      // Get the list of the average color difference of the each column
      val listDifferences:Seq[Double] =
        for(i <- 1 until raster.getHeight )
        yield {
          val columRef:Array[Double] =  raster.getPixels(0, i-1, img.getWidth, 1, null)
          val columCmp:Array[Double] =  raster.getPixels(0, i,   img.getWidth, 1, null)
          myCorrelation(columRef.toList, columCmp.toList)
        }
      // Get indexes of the changing column
      val res:Seq[Int] = for((elem:Double, idx:Int) <- listDifferences.zipWithIndex if elem >= threashold)
      yield {
        idx
      }
      // add the last edge
      (res :+ (raster.getHeight-1)).toList
    }

    /**
     * Get list of index when the pattern change in horizontal scanning (from left to right)
     */
    def getIndexesChangeHorizontal(img:BufferedImage, threashold:Double):List[Int] = {
      val raster:Raster = img.getRaster
      // Get the list of the average color difference of the each column
      val listDifferences:Seq[Double] =
        for(i <- 1 until raster.getWidth )
        yield {
          val columRef:Array[Double] =  raster.getPixels(i-1, 0, 1, img.getHeight, null)
          val columCmp:Array[Double] =  raster.getPixels(i, 0, 1, img.getHeight, null)
          myCorrelation(columRef.toList, columCmp.toList)
        }
      // Get indexes of the changing column
      val res:Seq[Int] = for((elem:Double, idx:Int) <- listDifferences.zipWithIndex if elem >= threashold)
      yield {
        idx
      }
      // add the last edge
      ( res :+ (raster.getWidth-1) ).toList
    }


    /**
     * Sum of the average number of color difference of two signal.
     *
     * It uses the absolut difference of color of each pixel and average the result.
     */
    def myCorrelation(sigA:List[Double], sigB:List[Double]): Double = {
      @tailrec
      def myCorRec(sigA:List[Double], sigB:List[Double], res:Double): Double = {
        (sigA, sigB) match {
          case (xa::xas, xb::xbs) =>  myCorRec(xas, xbs, res + (xa-xb).abs )
          case _ => res
        }
      }
      myCorRec(sigA, sigB, 0.0) / sigA.size
    }
  } /*End module EdgeDetector*/




}

