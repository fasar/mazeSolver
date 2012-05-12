package fsart.maze.app.gui.guiComponents

import scala.swing.{BoxPanel,Orientation,ListView,Label,ScrollBar}
import scala.swing.Dialog
import scala.swing.event.ListSelectionChanged
import java.awt.Color
import javax.swing.border.LineBorder
import fsart.maze.app.gui._
import java.awt.event.{AdjustmentListener, AdjustmentEvent}


/**
 * This is the singleton of the algo swing list and maze swing list items
 *
 * @author    fabien sartor
 * @version   1.0
 */
object selectorPanel extends BoxPanel(Orientation.Vertical) {

  //algo and maze Lists
  var listAlgo:ListView[String] = new ListView[String]{
    listData = ""::Nil
    border = new LineBorder(Color.gray)
    //selection.intervalMode = ListView.IntervalMode.Single
    fixedCellWidth = 160
    selection.reactions += {
      case ListSelectionChanged(listsrc:ListView[_], _, true) =>
        guiControler.defCurrentAlgo(listsrc.selection.items(0).asInstanceOf[String])
    }
  }

  var listMaze:ListView[String] = new ListView[String]{
    listData = ""::Nil
    border = new LineBorder(Color.gray)
    //selection.intervalMode = ListView.IntervalMode.Single
    fixedCellWidth = 160
    selection.reactions += {
      case ListSelectionChanged(listsrc:ListView[_], _, true) =>
        guiControler.defCurrentMaze(listMaze.selection.items(0).asInstanceOf[String])
    }
  }


  contents += new Label("Maze") {xLayoutAlignment = 0.5}
  contents += listMaze
  contents += new Label("Algorithm"){xLayoutAlignment = 0.5}
  contents += listAlgo




  var labelZoom = new Label("Image zoom"){xLayoutAlignment = 0.5}
  var scrollZoom:ScrollBar = new ScrollBar(){
    orientation = Orientation.Horizontal
    minimum = -100
    maximum = 110

    this.peer.addAdjustmentListener(new AdjustmentListener{
      def adjustmentValueChanged(e:AdjustmentEvent) {
        val zoom:Double = (e.getValue + 100.0 ) / 100.0
        mazePanel.scaleImg = zoom
        labelZoom.text = "Image zoom : " + (zoom * 100).toInt + "%"
      }
    })
    // reactions variable don't work. It's not impl in scala 2.9.
    // look code at: http://lampsvn.epfl.ch/trac/scala/browser/scala/tags/R_2_9_0_1/src//swing/scala/swing/ScrollBar.scala#L1
    // use old way to do that with scala code source ScrollBar.peer:JScrollBar
    // look this.peer.addAdjustmentListener(new AdjustmentListener{...
//    reactions += {
//        case e =>
//          println("Got event: " + e)
//          Dialog.showConfirmation(null, "Event : " + e, "Debug", Dialog.Options.Default, Dialog.Message.Info)
//    }
  }

  contents += labelZoom
  contents += scrollZoom



  var labelSpeed = new Label("Step speed"){xLayoutAlignment = 0.5}
  var scrollSpeed:ScrollBar = new ScrollBar(){
    orientation = Orientation.Horizontal
    minimum = -100
    maximum = 1010

    this.peer.addAdjustmentListener(new AdjustmentListener{
      def adjustmentValueChanged(e:AdjustmentEvent) {
        val speed:Int = e.getValue + 100
        MasterWindows.timeToWait = speed
        labelSpeed.text = "step speed : " + speed + " ms"
      }
    })
  }

  contents += labelSpeed
  contents += scrollSpeed

}

