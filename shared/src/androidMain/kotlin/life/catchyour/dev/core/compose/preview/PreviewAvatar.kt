@file:OptIn(ExperimentalFoundationApi::class)

package life.catchyour.dev.core.compose.preview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import life.catchyour.dev.core.compose.components.AvatarComposable
import life.catchyour.dev.core.compose.model.UiImage
import life.catchyour.dev.core.compose.theme.dimens


@Preview
@Composable
fun AvatarComposablePreview() {
    Column {
        AvatarComposable(
            size = dimens.sizeAvatar,
            firstLettersOfName = "JD",
            avatar = UiImage(
                id = "1",
                url = "https://www.example.com/avatar.jpg",
                originalFilename = "avatar.jpg"
            )
        )
    }
}

@Preview
@Composable
fun AvatarComposableImagePreview() {
    Column {
        AvatarComposable(
            size = dimens.sizeAvatar,
            firstLettersOfName = "JD"
        )
    }
}

@Preview
@Composable
fun AvatarComposableEmptyPreview() {
    Column {
        AvatarComposable(
            size = 32.dp
        )
    }
}
