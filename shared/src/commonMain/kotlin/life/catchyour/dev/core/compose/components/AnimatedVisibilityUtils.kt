package life.catchyour.dev.core.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun AnimatedVisibilityVertical(
    visible: Boolean,
    expandFrom: Alignment.Vertical = Alignment.Bottom,
    shrinkTowards: Alignment.Vertical = Alignment.Bottom,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(expandFrom = expandFrom),
        exit = shrinkVertically(shrinkTowards = shrinkTowards) + fadeOut()
    ) {
        content()
    }
}
