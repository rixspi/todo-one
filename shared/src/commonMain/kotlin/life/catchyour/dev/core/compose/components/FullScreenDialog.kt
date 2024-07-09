@file:OptIn(ExperimentalMaterial3Api::class)

package life.catchyour.dev.core.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import life.catchyour.dev.core.compose.theme.dimens
import org.jetbrains.compose.resources.stringResource
import projectname.shared.generated.resources.Cancel
import projectname.shared.generated.resources.Res
import projectname.shared.generated.resources.action_save

@Composable
fun FullScreenDialog(
    title: String?,
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false
    ),
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    overrideContent: Boolean = false,
    showCloseButton: Boolean = true,
    showSaveButton: Boolean = true,
    buttons: @Composable RowScope.() -> Unit = {
        if (showCloseButton) {
            IconButton(
                onClick = { onDismiss() }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(Res.string.Cancel)
                )
            }
        }
        if (title != null) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
        if (showSaveButton) {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(text = stringResource(Res.string.action_save))
            }
        }
    },
    content: @Composable () -> Unit,
) {
    BasicAlertDialog(
        properties = properties,
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier.padding(start = 4.dp, end = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                buttons()
            }
            Column(
                modifier = Modifier.then(
                    if (overrideContent) {
                        Modifier.padding(0.dp)
                    } else {
                        Modifier.padding(
                            bottom = dimens.screenPadding,
                            start = dimens.screenPadding,
                            end = dimens.screenPadding,
                        )
                    }
                )
            ) {
                content()
            }
        }

    }
}