package life.catchyour.dev

import androidx.compose.ui.platform.UriHandler

fun UriHandler.openAllUris(uri: String) {
    if (uri.isEmpty()) return

    val resultUri = if (uri.contains("http://") || uri.contains("https://")) {
        uri
    } else {
        "https://$uri"
    }

    try {
        openUri(resultUri)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}