package life.catchyour.dev

import android.content.Context
import android.content.pm.PackageManager

val String.Companion.empty
    get() = ""

fun Context.getMapsKey(): String {
    val applicationInfo = packageManager
        .getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    return applicationInfo.metaData?.getString("com.google.android.geo.API_KEY")
        ?: throw IllegalStateException("No maps key found in manifest.")
}