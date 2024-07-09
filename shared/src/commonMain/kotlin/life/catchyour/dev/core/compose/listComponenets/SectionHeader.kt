package life.catchyour.dev.core.compose.listComponenets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import life.catchyour.dev.core.compose.components.LinkifyText
import life.catchyour.dev.core.compose.theme.dimens

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    text: String?,
    linkify: Boolean = false
) {
    if (text.isNullOrEmpty()) {
        Spacer(modifier = Modifier.height(dimens.spacing7))
        return
    }

    val padding = modifier.padding(
        start = dimens.spacing6,
        bottom = dimens.spacing5
    )

    if (linkify) {
        LinkifyText(
            modifier = padding,
            text = text.uppercase(),
            style = MaterialTheme.typography.labelMedium
        )
    } else {
        Text(
            modifier = padding,
            text = text.uppercase(),
            style = MaterialTheme.typography.labelMedium
        )
    }
}