package life.catchyour.base.permissions

sealed class PermissionState {
    object Granted : PermissionState()
    data class NotGranted(val permissionRequestLauncher: () -> Unit) : PermissionState()
}
