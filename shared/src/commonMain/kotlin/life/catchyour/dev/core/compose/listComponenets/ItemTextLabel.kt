package life.catchyour.dev.core.compose.listComponenets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ItemTextLabel(
    modifier: Modifier = Modifier,
    text: String
) {
    if (text.isEmpty()) return
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.bodyLarge)
}