@file:OptIn(ExperimentalFoundationApi::class)

package life.catchyour.dev.core.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import coil3.request.crossfade
import life.catchyour.dev.core.compose.model.UiImage
import life.catchyour.dev.core.compose.theme.dimens

@ExperimentalFoundationApi
@Composable
fun AvatarComposable(
    modifier: Modifier = Modifier,
    firstLettersOfName: String? = null,
    size: Dp,
    avatar: UiImage? = null,
    onClick: () -> Unit = {}
) {
    val avatarImage by remember(avatar?.id) { mutableStateOf(avatar) }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.inversePrimary)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onClick
            )
            .padding(dimens.spacing1)
    ) {
        if (firstLettersOfName.isNullOrEmpty()) {
            AvatarEmptyComposable(
                size = size
            )
        } else {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(avatarImage?.url)
                    .crossfade(enable = true)
                    .diskCacheKey(avatarImage?.id)
                    .memoryCacheKey(avatarImage?.id)
                    .build(),
                contentDescription = "Avatar"
            ) {
                val state = painter.state
                when (state) {
                    AsyncImagePainter.State.Empty -> AvatarPlaceholderComposable(
                        firstLettersOfName = firstLettersOfName,
                        size = size
                    )

                    is AsyncImagePainter.State.Error -> AvatarPlaceholderComposable(
                        firstLettersOfName = firstLettersOfName,
                        size = size
                    )

                    is AsyncImagePainter.State.Loading -> CircularProgressIndicator(
                        modifier = Modifier
                            .size(dimens.spacing7)
                            .align(Alignment.Center)
                    )

                    is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun AvatarPlaceholderComposable(
    modifier: Modifier = Modifier,
    firstLettersOfName: String,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp
) {
    Box(
        modifier = modifier
            .size(size)
            .drawBehind {
                drawCircle(
                    color = color
                )
            }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = firstLettersOfName,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.contentColorFor(color),
            fontSize = (size.times(0.4f)).sp
        )
    }
}

@Composable
fun AvatarEmptyComposable(
    modifier: Modifier = Modifier,
    size: Dp
) {
    Icon(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.inversePrimary)
            .padding(dimens.spacing1),
        imageVector = Icons.Default.Person,
        contentDescription = "Avatar icon"
    )
}

val Int.dpTextUnit: TextUnit
    @Composable
    get() = with(LocalDensity.current) { this@dpTextUnit.dp.toSp() }

val Dp.sp: TextUnit
    @Composable
    get() = with(LocalDensity.current) { this@sp.toSp() }