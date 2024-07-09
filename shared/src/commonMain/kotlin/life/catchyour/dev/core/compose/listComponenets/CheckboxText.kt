package life.catchyour.dev.core.compose.listComponenets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import life.catchyour.dev.core.compose.theme.dimens

@Composable
fun CheckboxText(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = dimens.inFormElementStartPadding,
        vertical = dimens.spacing5
    ),
    onSelectedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = {
                    onSelectedChange(!isSelected)
                },
                role = Role.Checkbox
            )
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimens.spacing5)
    ) {
        Checkbox(checked = isSelected, onCheckedChange = null)
        Text(text = text)
    }
}