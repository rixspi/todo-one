package life.catchyour.dev.core.compose.preview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import life.catchyour.dev.core.compose.components.FullScreenDialog
import life.catchyour.dev.core.compose.components.NormalDialog

@Preview(showBackground = true)
@Composable
fun EventoDialogPreview() {
    NormalDialog(
        title = "Title"
    ) {
        Text(
            text = "Content longer text. To see how this all looks. That's not easy.",
            lineHeight = 1.5.em
        )
    }
}


@Preview(showBackground = true)
@Composable
fun EventoFullScreenDialogPreview() {
    FullScreenDialog(title = "Title") {
        Text(text = "Content longer text. To see how this all looks. That's not easy.")
    }
}
