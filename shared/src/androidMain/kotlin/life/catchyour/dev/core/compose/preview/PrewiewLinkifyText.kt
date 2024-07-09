package life.catchyour.dev.core.compose.preview

import androidx.compose.runtime.Composable
import life.catchyour.dev.core.compose.components.LinkifyText

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun PreviewLinkifyText() {
    LinkifyText(text = "https://www.google.com")
}
