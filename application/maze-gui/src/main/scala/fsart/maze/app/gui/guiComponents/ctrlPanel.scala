package fsart.maze.app.gui.guiComponents

import swing.{Button, FlowPanel, Dialog}
import swing.event.ButtonClicked
import fsart.maze.app.gui.MasterWindows

/**
 * This is a singleton for the control panel.
 * Control panel contains some buttons
 *
 * @author  fabien sartor
 * @version   1.0
 */
object ctrlPanel extends  FlowPanel {
    val exit_bt = new Button {
      text = "Close"
      reactions += {
        case ButtonClicked(b2) =>  MasterWindows.quit()
      }
    }
    contents += exit_bt

    // Button cmd
    val slower_bt = new Button {
      text = "Slower"
      reactions += {
        case ButtonClicked(b2) => if(MasterWindows.timeToWait<=5000) MasterWindows.timeToWait+=100
      }
    }
    val quicker_bt = new Button {
      text = "Quicker"
      reactions += {
        case ButtonClicked(b2) => if(MasterWindows.timeToWait>=100) MasterWindows.timeToWait-=100
      }
    }

    contents += slower_bt
    contents += quicker_bt

    // Useful to test new features
//    val debug_bt = new Button {
//      text = "debug"
//      reactions += {
//        case ButtonClicked(b2) =>
//          Dialog.showConfirmation(null, "Message de debug", "Debug", Dialog.Options.Default, Dialog.Message.Info)
//      }
//    }
//    contents += debug_bt
  }
