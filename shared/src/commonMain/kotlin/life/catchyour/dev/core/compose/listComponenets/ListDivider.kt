package life.catchyour.dev.core.compose.listComponenets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import life.catchyour.dev.core.compose.theme.dimens

@Composable
fun ListDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier
            .fillMaxWidth()
            .height(dimens.sizeSeparator)
            .alpha(0.3f)
    )
}
