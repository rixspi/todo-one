package life.catchyour.dev.core.compose.listComponenets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ItemTextHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    if (text.isEmpty()) return
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
}