package life.catchyour

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import life.catchyour.dev.core.compose.components.FullScreenDialog
import life.catchyour.dev.core.compose.components.NormalDialog
import life.catchyour.dev.todo.TodoOneComposeApp
import java.awt.Dimension
import java.awt.Toolkit

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "To Do One",
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = getPreferredWindowSize(720, 857)
        ),
        //icon = painterResource(Res.drawable.ic_imageviewer_round),
        // https://github.com/JetBrains/compose-jb/issues/2741
        onKeyEvent = {
//            if (it.type == KeyEventType.KeyUp) {
//                when (it.key) {
//                    Key.DirectionLeft -> externalNavigationEventBus.produceEvent(
//                        ExternalImageViewerEvent.Previous
//                    )
//
//                    Key.DirectionRight -> externalNavigationEventBus.produceEvent(
//                        ExternalImageViewerEvent.Next
//                    )
//
//                    Key.Escape -> externalNavigationEventBus.produceEvent(
//                        ExternalImageViewerEvent.ReturnBack
//                    )
//                }
//            }
            false
        }
    ) {
        TodoOneComposeApp()
    }
}

private fun getPreferredWindowSize(desiredWidth: Int, desiredHeight: Int): DpSize {
    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    val preferredWidth: Int = (screenSize.width * 0.8f).toInt()
    val preferredHeight: Int = (screenSize.height * 0.8f).toInt()
    val width: Int = if (desiredWidth < preferredWidth) desiredWidth else preferredWidth
    val height: Int = if (desiredHeight < preferredHeight) desiredHeight else preferredHeight
    return DpSize(width.dp, height.dp)
}
