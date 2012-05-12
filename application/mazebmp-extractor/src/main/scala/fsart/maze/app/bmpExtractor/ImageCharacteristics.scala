package fsart.maze.app.bmpExtractor

import java.awt.image.{WritableRaster, Raster, BufferedImage}


object ImageCharacteristics {
  /**
   * retrun a list of the average grey in each line
   */
  def getLineSig(img:BufferedImage):List[Double] = {
    val raster:Raster = img.getRaster

    val res:Seq[Double] =
      for(i <- 0 until raster.getHeight )
      yield {
        val line:Array[Int] =  raster.getPixels(0, i, img.getWidth, 1, null)
        getPixSignature(line)
      }
    res.toList
  }

  /**
   * retrun a list of the average grey in each column
   */
  def getColumnSig(img:BufferedImage):List[Double] = {
    val raster:Raster = img.getRaster

    val res:Seq[Double] =
      for(i <- 0 until raster.getWidth )
      yield {
        val colum:Array[Int] =  raster.getPixels(i, 0, 1, img.getHeight, null)
        getPixSignature(colum)
      }
    res.toList
  }

  /**
   * Return the avergage of grey of the grey pixels passed in param
   *
   */
  private def getPixSignature(pixels:Array[Int]):Double = {
    val res:Double = (0.0 /: pixels) { _ + _ }
    (res / pixels.size)
  }


  /**
   * Generate a image with the percentage of each elem in the list as a column.
   * It is useful to draw the percentage of black pixel from the function getColumnSig
   */
  def getSigImage(a:List[Double], height:Int): BufferedImage = {
    val img:BufferedImage = new BufferedImage(a.size, height, BufferedImage.TYPE_BYTE_GRAY)
    val rast:WritableRaster = img.getRaster
    for((elem:Double, pos:Int) <- a.zipWithIndex) {
      val mid:Int = (elem /255.0 * height).floor.toInt
      for(h <- 0 until mid) {
        rast.setPixel(pos, h, Array(255))
      }
      for(h <- mid until height) {
        rast.setPixel(pos, h, Array(0))
      }
    }
    img
  }
}
