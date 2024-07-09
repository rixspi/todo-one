@file: Suppress("MagicNumber")

package life.catchyour.dev.core.compose.picker

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

/**
 * A composable function that allows users to select an item from a list using a scrollable list with a text field for editing.
 *
 * @param initialValue The initial value to be selected in the list.
 * @param list An immutable wrapper containing the list of items.
 * @param modifier Modifier for customizing the appearance of the `ListPicker`.
 * @param wrapSelectorWheel Boolean flag indicating whether the list should wrap around like a selector wheel.
 * @param format A lambda function that formats an item into a string for display.
 * @param onValueChange A callback function that is invoked when the selected item changes.
 * @param parse A lambda function that parses a string into an item.
 * @param enableEdition Boolean flag indicating whether the user can edit the selected item using a text field.
 * @param outOfBoundsPageCount The number of pages to display on either side of the selected item.
 * @param textStyle The text style for the displayed items.
 * @param verticalPadding The vertical padding between items.
 * @param dividerColor The color of the horizontal dividers.
 * @param dividerThickness The thickness of the horizontal dividers.
 *
 * @author Reda El Madini - For support, contact gladiatorkilo@gmail.com
 */

// TODO Not tested

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <E> ListPicker(
    initialValue: E,
    list: ImmutableWrapper<List<E>>,
    modifier: Modifier = Modifier,
    wrapSelectorWheel: Boolean = false,
    format: E.() -> String = { toString() },
    onValueChange: (E) -> Unit,
    parse: (String.() -> E?)? = null,
    enableEdition: Boolean = parse != null,
    outOfBoundsPageCount: Int = 1,
    textStyle: TextStyle = LocalTextStyle.current,
    verticalPadding: Dp = 16.dp,
    dividerColor: Color = MaterialTheme.colorScheme.outline,
    dividerThickness: Dp = 1.dp
) {
    val items = list.value
    val listSize = items.size
    val coercedOutOfBoundsPageCount = outOfBoundsPageCount.coerceIn(0..listSize / 2)
    val visibleItemsCount = 1 + coercedOutOfBoundsPageCount * 2
    val iteration =
        if (wrapSelectorWheel)
            remember(key1 = coercedOutOfBoundsPageCount, key2 = listSize) {
                (Int.MAX_VALUE - 2 * coercedOutOfBoundsPageCount) / listSize
            }
        else 1
    val intervals =
        remember(key1 = coercedOutOfBoundsPageCount, key2 = iteration, key3 = listSize) {
            listOf(
                0,
                coercedOutOfBoundsPageCount,
                coercedOutOfBoundsPageCount + iteration * listSize,
                coercedOutOfBoundsPageCount + iteration * listSize + coercedOutOfBoundsPageCount,
            )
        }
    val scrollOfItemIndex = { it: Int ->
        it + (listSize * (iteration / 2))
    }
    val scrollOfItem = { item: E ->
        items.indexOf(item)
            .takeIf { it != -1 }
            ?.let { index -> scrollOfItemIndex(index) }
    }
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = remember(
            key1 = initialValue,
            key2 = listSize,
            key3 = iteration,
        ) {
            scrollOfItem(initialValue) ?: 0
        },
    )
    LaunchedEffect(key1 = list) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }.collectLatest {
            onValueChange(items[it % listSize])
        }
    }
    val itemHeight = with(LocalDensity.current) {
        textStyle.lineHeight.toDp()
    } + verticalPadding * 2
    var edit by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = edit,
            label = "AnimatedContent",
        ) { showTextField ->
            if (showTextField) {
                var isError by rememberSaveable { mutableStateOf(false) }
                val initialSelectedItem = remember {
                    items[lazyListState.firstVisibleItemIndex % listSize]
                }
                var value by rememberSaveable {
                    mutableStateOf(initialSelectedItem.format())
                }
                val focusRequester = remember { FocusRequester() }
                LaunchedEffect(key1 = Unit) {
                    focusRequester.requestFocus()
                }
                val coroutineScope = rememberCoroutineScope()
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = value,
                    onValueChange = { string ->
                        value = string
                        parse?.invoke(string).let { item ->
                            isError =
                                if (item != null)
                                    if (items.contains(item)) false
                                    else true // item not found
                                else true // string not parcelable

                            if (isError) onValueChange(initialSelectedItem)
                            else onValueChange(item ?: initialSelectedItem)
                        }
                    },
                    textStyle = textStyle.copy(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (!isError)
                                parse?.invoke(value)?.let { item ->
                                    scrollOfItem(item)?.let { scroll ->
                                        coroutineScope.launch {
                                            lazyListState.scrollToItem(scroll)
                                        }
                                    }
                                }
                            edit = false
                        }
                    ),
                    isError = isError,
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        errorTextColor = MaterialTheme.colorScheme.error,
                    ),
                )
            } else {
                LazyColumn(
                    state = lazyListState,
                    flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight * visibleItemsCount)
                        .fadingEdge(
                            brush = remember {
                                Brush.verticalGradient(
                                    0F to Color.Transparent,
                                    0.5F to Color.Black,
                                    1F to Color.Transparent
                                )
                            },
                        ),
                ) {
                    items(
                        count = intervals.last(),
                        key = { it },
                    ) { index ->
                        val enabled by remember(index, enableEdition) {
                            derivedStateOf {
                                enableEdition && !edit && (index == lazyListState.firstVisibleItemIndex + coercedOutOfBoundsPageCount)
                            }
                        }
                        val textModifier = Modifier.padding(vertical = verticalPadding)
                        when (index) {
                            in intervals[0]..<intervals[1] -> Text(
                                text = if (wrapSelectorWheel) items[(index - coercedOutOfBoundsPageCount + listSize) % listSize].format() else "",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = textStyle,
                                modifier = textModifier,
                            )

                            in intervals[1]..<intervals[2] -> {
                                Text(
                                    text = items[(index - coercedOutOfBoundsPageCount) % listSize].format(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = textStyle,
                                    modifier = textModifier.then(
                                        Modifier.clickable(
                                            onClick = { edit = true },
                                            enabled = enabled,
                                        )
                                    ),
                                )
                            }

                            in intervals[2]..<intervals[3] -> Text(
                                text = if (wrapSelectorWheel) items[(index - coercedOutOfBoundsPageCount) % listSize].format() else "",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = textStyle,
                                modifier = textModifier,
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.offset(y = itemHeight * coercedOutOfBoundsPageCount - dividerThickness / 2),
                    thickness = dividerThickness,
                    color = dividerColor,
                )

                HorizontalDivider(
                    modifier = Modifier.offset(y = itemHeight * (coercedOutOfBoundsPageCount + 1) - dividerThickness / 2),
                    thickness = dividerThickness,
                    color = dividerColor,
                )
            }
        }
    }
}

@Stable
fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Immutable
data class ImmutableWrapper<T>(val value: T)

fun <T> T.toImmutableWrapper() = ImmutableWrapper(this)

operator fun <T> ImmutableWrapper<T>.getValue(thisRef: Any?, property: KProperty<*>) = value