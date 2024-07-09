package life.catchyour.dev.core.compose.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.minus
import life.catchyour.dev.core.compose.picker.ListPicker
import life.catchyour.dev.core.compose.picker.toImmutableWrapper
import life.catchyour.dev.core.util.now

@Preview
@Composable
fun PreviewListPicker1() {
    Surface(color = MaterialTheme.colorScheme.primary) {
        var value by remember { mutableStateOf(LocalDate.now()) }
        val list = remember {
            buildList {
                repeat(10) {
                    add(
                        LocalDate.now().minus(it - 5, DateTimeUnit.DAY),
                    )
                }
            }
        }
        ListPicker(
            initialValue = value,
            list = list.toImmutableWrapper(),
            wrapSelectorWheel = true,
            format = {
                format(
                    LocalDate.Formats.ISO
                )
            },
            onValueChange = { value = it },
            textStyle = TextStyle(fontSize = 32.sp),
            verticalPadding = 8.dp,
        )
    }
}


@Preview()
@Composable
fun PreviewListPicker2() {
    Surface(color = MaterialTheme.colorScheme.tertiary) {
        var value by remember { mutableStateOf("5") }
        val list = remember { (1..10).map { it.toString() } }
        ListPicker(
            initialValue = value,
            list = list.toImmutableWrapper(),
            modifier = Modifier,
            onValueChange = { value = it },
            outOfBoundsPageCount = 2,
            textStyle = TextStyle(fontSize = 32.sp),
            verticalPadding = 8.dp,
        )
    }
}

@Preview
@Composable
fun PreviewListPicker3() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var value by remember { mutableIntStateOf(5) }
        val list = remember { (1..10).map { it } }

        Surface(color = MaterialTheme.colorScheme.primary) {
            Text(
                text = "Selected value: $value",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(fontSize = 32.sp).copy(
                    textAlign = TextAlign.Center,
                ),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface {
            ListPicker(
                initialValue = value,
                list = list.toImmutableWrapper(),
                format = { this.toString() },
                onValueChange = { value = it },
                parse = {
                    takeIf {
                        // check if each input string contains only integers
                        it.matches(Regex("^\\d+\$"))
                    }?.toInt()
                },
                outOfBoundsPageCount = 2,
                textStyle = TextStyle(fontSize = 32.sp),
                verticalPadding = 8.dp,
            )
        }
    }
}
