package life.catchyour.dev.core.compose.listComponenets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import life.catchyour.dev.core.compose.conditional
import life.catchyour.dev.core.compose.theme.dimens

@Composable
fun NavigableItemRow(
    header: String? = "Header",
    text: String? = "Value"
) {
    NavigableItemRowComposable {
        if (header?.isNotEmpty() == true) {
            Text(
                modifier = Modifier
                    .padding(
                        end = dimens.spacing5,
                        bottom = dimens.spacing3
                    ),
                text = header,
                style = MaterialTheme.typography.labelLarge,
            )
        }
        if (text?.isNotEmpty() == true) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun NavigableItemRowComposable(
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .conditional(onClick != null) {
                clickable { onClick?.invoke() }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            content()
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ChevronRight,
            contentDescription = "Right arrow"
        )
    }
}

val Icons.AutoMirrored.Filled.ChevronRight: ImageVector
    get() = ImageVector.Builder(
        name = "chevron_right",
        defaultWidth = 40.0.dp,
        defaultHeight = 40.0.dp,
        viewportWidth = 40.0f,
        viewportHeight = 40.0f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1.0f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(14.75f, 28.875f)
            quadToRelative(-0.375f, -0.417f, -0.396f, -0.937f)
            quadToRelative(-0.021f, -0.521f, 0.396f, -0.938f)
            lineToRelative(7.042f, -7.042f)
            lineToRelative(-7.084f, -7.041f)
            quadToRelative(-0.375f, -0.375f, -0.354f, -0.938f)
            quadToRelative(0.021f, -0.562f, 0.396f, -0.937f)
            quadToRelative(0.417f, -0.417f, 0.938f, -0.417f)
            quadToRelative(0.52f, 0f, 0.895f, 0.417f)
            lineToRelative(8.042f, 8f)
            quadToRelative(0.208f, 0.208f, 0.292f, 0.437f)
            quadToRelative(0.083f, 0.229f, 0.083f, 0.479f)
            quadToRelative(0f, 0.292f, -0.083f, 0.5f)
            quadToRelative(-0.084f, 0.209f, -0.292f, 0.417f)
            lineToRelative(-8f, 8f)
            quadToRelative(-0.417f, 0.417f, -0.937f, 0.396f)
            quadToRelative(-0.521f, -0.021f, -0.938f, -0.396f)
            close()
        }
    }.build()


