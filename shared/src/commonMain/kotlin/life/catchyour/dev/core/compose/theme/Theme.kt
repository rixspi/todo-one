package life.catchyour.dev.core.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    getColorScheme: @Composable () -> ColorScheme = {
        if (darkTheme) darkColorScheme() else lightColorScheme()
    },
    specialEffects: @Composable (colorScheme: ColorScheme) -> Unit = {},
    content: @Composable () -> Unit
) {
    val colorScheme = getColorScheme()
    specialEffects(colorScheme)

    ProvideDimens(dimensions = defaultDimensions) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography(),
            content = content
        )
    }
}

private val localAppDimens = staticCompositionLocalOf {
    defaultDimensions
}

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }

    CompositionLocalProvider(localAppDimens provides dimensionSet, content = content)
}

val dimens: Dimensions
    @Composable
    get() = localAppDimens.current


val ColorScheme.warning: Color
    @Composable
    @ReadOnlyComposable
    get() = Warning

val ColorScheme.lightWarning: Color
    @Composable
    @ReadOnlyComposable
    get() = light_Warning

