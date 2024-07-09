package life.catchyour.dev.core.compose.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val spacing0: Dp,
    val spacing1: Dp,
    val spacing2: Dp,
    val spacing3: Dp,
    val spacing4: Dp,
    val spacing5: Dp,
    val spacing6: Dp,
    val spacing7: Dp,
    val spacing8: Dp,
    val sizeSeparator: Dp,
    val sizeAvatar: Dp,
) {
    val screenPadding = spacing5

    val listDividerHeight = sizeSeparator

    val columnElementsSpacing = spacing3

    val inFormElementStartPadding = spacing4
}

val defaultDimensions = Dimensions(
    spacing0 = 0.dp,
    spacing1 = 2.dp,
    spacing2 = 4.dp,
    spacing3 = 8.dp,
    spacing4 = 12.dp,
    spacing5 = 16.dp,
    spacing6 = 20.dp,
    spacing7 = 24.dp,
    spacing8 = 28.dp,
    sizeSeparator = 1.dp,
    sizeAvatar = 128.dp
)
