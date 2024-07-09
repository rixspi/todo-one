package life.catchyour.dev.core.compose.listComponenets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import life.catchyour.dev.core.compose.conditional

@Composable
fun PaddingItemContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    isClickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .conditional(isClickable) { clickable { onClick() } }
    ) {
        content()
    }
}