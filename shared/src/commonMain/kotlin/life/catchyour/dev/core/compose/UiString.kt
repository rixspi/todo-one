package life.catchyour.dev.core.compose

import androidx.compose.runtime.Composable
import life.catchyour.dev.core.util.empty
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed class UiString private constructor() {

    data object Empty : UiString()

    data class ResourceString(val id: StringResource, val formatArgs: List<Any> = emptyList()) :
        UiString()

    data class Text(val text: String) : UiString()

    @Composable
    fun asString() = when (this) {
        is ResourceString -> stringResource(resource = id, *formatArgs.toTypedArray())
        is Empty -> String.empty
        is Text -> text
    }

    companion object {
        operator fun invoke(id: StringResource, formatArgs: List<Any> = emptyList()): UiString =
            ResourceString(id, formatArgs)

        operator fun invoke(text: String): UiString = Text(text)
        operator fun invoke(): UiString = Empty
    }
}

fun String?.toUiString(): UiString? = if (this == null) null else UiString.Text(this)

fun UiString.isEmpty(): Boolean =
    this is UiString.Empty || (this is UiString.Text && this.text.isEmpty())

fun UiString.isNotEmpty(): Boolean =
    (this is UiString.Text && this.text.isNotEmpty()) || this !is UiString.Empty
