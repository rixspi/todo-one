@file:OptIn(ExperimentalMaterial3Api::class)

package life.catchyour.dev.core.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import projectname.shared.generated.resources.Ok
import projectname.shared.generated.resources.Res

@Composable
fun NormalDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    properties: DialogProperties = DialogProperties(),
    showCloseButton: Boolean = true,
    showOkButton: Boolean = true,
    okButtonText: String = stringResource(Res.string.Ok),
    cancelButtonText: String = stringResource(Res.string.Cancel),
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    overrideContent: Boolean = false,
    buttons: @Composable RowScope.() -> Unit = {
        if (showCloseButton) {
            TextButton(onClick = { onDismiss() }) {
                Text(cancelButtonText)
            }
        }
        if (showOkButton) {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(okButtonText)
            }
        }
    },
    content: @Composable () -> Unit,
) {
    BasicAlertDialog(
        properties = properties,
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .then(
                        if (overrideContent) Modifier.padding(0.dp) else Modifier.padding(
                            top = 24.dp,
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 8.dp
                        )
                    )
            ) {
                if (overrideContent) {
                    content()
                } else {
                    EventoDialogBody(title, content)
                    Spacer(modifier = Modifier.height(dimens.spacing7))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) { buttons() }
                }
            }
        }
    }
}

@Composable
fun EventoDialogBody(
    title: String?,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        if (title != null) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = dimens.spacing7),
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
        content()
    }
}
