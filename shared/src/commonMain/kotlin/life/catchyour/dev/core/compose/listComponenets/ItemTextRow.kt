package life.catchyour.dev.core.compose.listComponenets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.style.TextOverflow
import life.catchyour.dev.core.compose.components.LinkifyText
import life.catchyour.dev.core.compose.theme.dimens
import life.catchyour.dev.core.util.empty
import org.jetbrains.compose.resources.stringResource
import projectname.shared.generated.resources.Res
import projectname.shared.generated.resources.show_less
import projectname.shared.generated.resources.show_more

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemTextRow(
    modifier: Modifier = Modifier,
    header: String?,
    text: String?,
    displayLinks: Boolean = false,
) {
    var firstRun by remember { mutableStateOf(true) }
    var showMore by remember { mutableStateOf(ShowMoreState.NONE) }
    var shouldDraw by remember { mutableStateOf(false) }
    var lineCount by remember { mutableIntStateOf(0) }

    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.Center
    ) {
        if (header?.isNotEmpty() == true) {
            ItemTextHeader(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(
                        end = dimens.spacing3,
                        bottom = if (lineCount > 1) dimens.spacing3 else dimens.spacing0
                    ),
                text = header
            )
        }

        Column(
            modifier = Modifier.then(
                if (showMore == ShowMoreState.SHOW_MORE || lineCount > 1) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier.wrapContentWidth()
                }
            )
        ) {
            if (displayLinks) {
                LinkifyText(
                    modifier = Modifier
                        .drawWithContent {
                            if (shouldDraw) {
                                drawContent()
                            }
                        },
                    text = text ?: String.empty,
                    maxLines = if (showMore == ShowMoreState.SHOW_MORE) 5 else Int.MAX_VALUE,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    onTextLayout = { result ->
                        if (result.lineCount > 5 && firstRun) {
                            showMore = ShowMoreState.SHOW_MORE
                        }
                        lineCount = result.lineCount
                        shouldDraw = firstRun.not() || result.lineCount <= 5
                        firstRun = false
                    }
                )
            } else {

                Text(
                    modifier = Modifier
                        .drawWithContent {
                            if (shouldDraw) {
                                drawContent()
                            }
                        },
                    text = text ?: String.empty,
                    maxLines = if (showMore == ShowMoreState.SHOW_MORE) 5 else Int.MAX_VALUE,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    onTextLayout = { result ->
                        if (result.lineCount > 5 && firstRun) {
                            showMore = ShowMoreState.SHOW_MORE
                        }
                        lineCount = result.lineCount
                        shouldDraw = firstRun.not() || result.lineCount <= 5
                        firstRun = false
                    }
                )

            }

            if (showMore != ShowMoreState.NONE) {
                Text(
                    modifier = Modifier
                        .clickable {
                            showMore =
                                if (showMore == ShowMoreState.SHOW_MORE) {
                                    ShowMoreState.SHOW_LESS
                                } else {
                                    ShowMoreState.SHOW_MORE
                                }
                        }
                        .padding(top = dimens.spacing3),
                    text = if (showMore == ShowMoreState.SHOW_MORE) {
                        stringResource(Res.string.show_more)
                    } else {
                        stringResource(
                            Res.string.show_less
                        )
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun TitleTextRow(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = dimens.inFormElementStartPadding),
        text = text,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun SubtitleTextRow(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = dimens.inFormElementStartPadding),
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
}

enum class ShowMoreState {
    SHOW_MORE,
    SHOW_LESS,
    NONE
}
