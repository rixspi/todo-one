package life.catchyour.dev.core.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import projectname.shared.generated.resources.Res
import projectname.shared.generated.resources.str_template

@ExperimentalMaterial3Api
@Composable
fun BasicTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onStartNavClick: () -> Unit = {},
    onMiddleActionClick: () -> Unit = {},
    onEndActionClick: () -> Unit = {},
    showMiddleActionButton: Boolean = false,
    showEndActionButton: Boolean = true,
    startNavButtonIcon: @Composable (onClick: () -> Unit) -> Unit = {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            IconButton(
                onClick = { onStartNavClick() }) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        }
    },
    middleActionButtonIcon: @Composable (onClick: () -> Unit) -> Unit = {
        IconButton(
            onClick = { onMiddleActionClick() }) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close"
            )
        }
    },
    endActionTextButton: @Composable (onClick: () -> Unit) -> Unit = {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            TextButton(onClick = {
                onEndActionClick()
            }) {
                Text(text = stringResource(Res.string.str_template, "End", 2))
            }
        }
    },
) {
    TopAppBar(
        modifier = modifier.padding(start = 4.dp, end = 4.dp),
        title = {
            Text(text = title)
        },
        navigationIcon = {
            startNavButtonIcon(onStartNavClick)
        },
        actions = {
            if (showMiddleActionButton) {
                middleActionButtonIcon(onMiddleActionClick)
            }

            if (showEndActionButton) {
                endActionTextButton(onEndActionClick)
            }
        }
    )
}