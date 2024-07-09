package life.catchyour.dev.core.compose.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpacer(size: Dp = dimens.spacing5) {
    Spacer(modifier = Modifier.height(size))
}

@Composable
fun BigVerticalSpacer() {
    Spacer(modifier = Modifier.height(dimens.spacing7))
}

@Composable
fun SmallVerticalSpacer() {
    Spacer(modifier = Modifier.height(dimens.spacing3))
}

@Composable
fun HorizontalSpacer(size: Dp = dimens.spacing5) {
    Spacer(modifier = Modifier.width(size))
}

@Composable
fun BigHorizontalSpacer() {
    Spacer(modifier = Modifier.width((dimens.spacing7)))
}

@Composable
fun SmallHorizontalSpacer() {
    Spacer(modifier = Modifier.width((dimens.spacing3)))
}