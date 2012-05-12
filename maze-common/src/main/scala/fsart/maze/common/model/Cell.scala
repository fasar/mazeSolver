
package fsart.maze.common.model

object KindCell extends Enumeration {
  val Wall, Floor = Value
}


sealed abstract class Cell(kindCell: KindCell.Value) {

  def coord: Coord
  def getCoord: Coord = coord
  def getKindCell = kindCell

  override def equals(that : Any): Boolean = {
    that match {
      case d: Cell =>
        d.canEqual(this)
        this.getCoord.x==d.getCoord.x &&
        this.getCoord.y==d.getCoord.y &&
        this.kindCell == d.getKindCell
      case _ => false
    }
  }

  def canEqual(other: Any) = other.isInstanceOf[Cell]

  override def hashCode(): Int = ( this.coord.x + (this.coord.y + 41)*41 )*41
}




case class Wall(val coord: Coord) extends Cell(KindCell.Wall)

object Wall {
  def apply(x: Int, y:Int):Wall = new Wall(Coord(x,y))
}

case class Floor(val coord: Coord) extends Cell(KindCell.Floor)

object Floor {
  def apply(x: Int, y:Int):Floor = new Floor(Coord(x,y))
}






