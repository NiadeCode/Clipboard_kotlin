import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.util.logging.Level
import java.util.logging.Logger

class ClipBoardListener : Thread(), ClipboardOwner {
    var sysClip = Toolkit.getDefaultToolkit().systemClipboard
    override fun run() {
        val trans = sysClip.getContents(this)
        takeOwnership(trans)
    }

    override fun lostOwnership(c: Clipboard, t: Transferable) {
        try {
            sleep(250) //waiting e.g for loading huge elements like word's etc.
        } catch (e: Exception) {
            println("Exception: $e")
        }
        val contents = sysClip.getContents(this)
        try {
            processClipboard(contents, c)
        } catch (ex: Exception) {
            Logger.getLogger(ClipBoardListener::class.java.name).log(Level.SEVERE, null, ex)
        }
        takeOwnership(contents)
    }

    private fun takeOwnership(t: Transferable?) {
        sysClip.setContents(t, this)
    }

    private fun processClipboard(transferable: Transferable?, c: Clipboard?) { //your implementation
        val tempText: String
        try {
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                tempText = transferable.getTransferData(DataFlavor.stringFlavor) as String
                println(tempText)
            }
        } catch (e: Exception) {
        }
    }
}