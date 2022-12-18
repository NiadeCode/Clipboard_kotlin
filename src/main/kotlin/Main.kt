import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.FlavorEvent
import java.awt.datatransfer.StringSelection
import java.lang.Thread.sleep


fun main(args: Array<String>) {

    Toolkit.getDefaultToolkit().systemClipboard.addFlavorListener { flavorEvent: FlavorEvent ->
        println("ClipBoard UPDATED: " + flavorEvent.source + " " + flavorEvent.toString())

        when (val clipboard = flavorEvent.source) {
            is Clipboard -> {
                val transferable = clipboard.getContents(null)
                println(DataFlavor.stringFlavor.getReaderForText(transferable).readText())
            }
        }
    }

    val daemonThread = ClipBoardListener()
    daemonThread.start()

    sleep(20000L)

//    getValueFromClipboard()
//
//    val value = "texto de prueba"
//
//    //setValueToClipboard(value)
}

private fun setValueToClipboard(value: String) {
    Toolkit.getDefaultToolkit().systemClipboard.setContents(
        StringSelection(value), null
    )
}

private fun getValueFromClipboard() {
    val value = Toolkit.getDefaultToolkit().systemClipboard.getContents(null)
    println(DataFlavor.stringFlavor.getReaderForText(value).readText())
}