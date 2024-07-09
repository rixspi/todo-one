package life.catchyour.dev.core.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BetterDragHandle(
    modifier: Modifier = Modifier,
    width: Dp = 32.dp,
    height: Dp = 4.dp,
    opacity: Float = 0.4f,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = opacity)
) {
    Surface(
        modifier = modifier
            .semantics { contentDescription = "Customizable drag handle" },
        color = color,
        shape = shape
    ) {
        Box(
            Modifier
                .size(
                    width = width,
                    height = height
                )
        )
    }
}


