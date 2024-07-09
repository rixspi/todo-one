package life.catchyour.dev.core.compose.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import life.catchyour.dev.core.compose.listComponenets.NavigableItemRow

@Composable
@Preview(name = "navigationBoy")
fun PreviewNavigableItemRow(
    header: String? = "Header",
    text: String? = "Value"
) {
    NavigableItemRow()
}