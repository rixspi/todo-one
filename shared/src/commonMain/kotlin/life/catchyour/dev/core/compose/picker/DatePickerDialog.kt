package life.catchyour.dev.core.compose.picker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import projectname.shared.generated.resources.Cancel
import projectname.shared.generated.resources.Ok
import projectname.shared.generated.resources.Res

enum class DatePickerMode {
    BIG,
    SMALL
}

@ExperimentalMaterial3Api
@Composable
fun DatePickerDialog(
    initialDate: LocalDate,
    datePickerMode: DatePickerMode,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds(),
        initialDisplayMode = if (datePickerMode == DatePickerMode.SMALL) DisplayMode.Input else DisplayMode.Picker,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean = true
        }
    )

    val selectedDate: Instant = datePickerState.selectedDateMillis?.let {
        Instant.fromEpochMilliseconds(it)
    } ?: Clock.System.now()

    DatePickerDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                val localDate = selectedDate.toLocalDateTime(TimeZone.UTC).date
                onDateSelected(localDate)
                onDismiss()
            }) {
                Text(text = stringResource(resource = Res.string.Ok))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(resource = Res.string.Cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = datePickerMode == DatePickerMode.BIG,
        )
    }
}
