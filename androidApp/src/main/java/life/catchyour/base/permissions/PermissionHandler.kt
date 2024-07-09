package life.catchyour.base.permissions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import life.catchyour.R
import life.catchyour.dev.core.compose.components.NormalDialog
import life.catchyour.dev.core.compose.theme.dimens

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun PermissionHandler(
    requiredPermissions: List<String>,
    dialogTitle: String,
    dialogMessage: String,
    permanentlyDeniedMessage: String,
    autoRequestPermissionOnStart: Boolean = false,
    content: @Composable (permissionState: PermissionState) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }
    var snackBarSettingsShown by remember {
        mutableStateOf(false)
    }

    val permissionsState = rememberMultiplePermissionsState(
        permissions = requiredPermissions.toList(),
        onPermissionsResult = { permissions ->
            permissions.entries.filter { permissionResult ->
                !permissionResult.value
            }.firstOrNull { permissionResult ->
                // currently only way to know should we navigate to settings is to compare
                // shouldShowRequestPermissionRationale before and after calling permission request
                !ActivityCompat.shouldShowRequestPermissionRationale(
                    context.getActivity(),
                    permissionResult.key
                )
            }?.let {
                snackBarSettingsShown = true
            }
        }
    )

    Box {
        if (snackBarSettingsShown) {
            val openAppSettingsString = stringResource(R.string.open_app_settings)

            LaunchedEffect(key1 = snackBarHostState) {
                val result = snackBarHostState
                    .showSnackbar(
                        message = permanentlyDeniedMessage,
                        actionLabel = openAppSettingsString,
                        duration = SnackbarDuration.Short
                    )
                snackBarSettingsShown = when (result) {
                    SnackbarResult.Dismissed -> {
                        false
                    }

                    SnackbarResult.ActionPerformed -> {
                        context.openApplicationSettings()
                        false
                    }
                }
            }
        }

        LaunchedEffect(key1 = permissionsState) {
            permissionsState.launchMultiplePermissionRequest()
        }

        if (showDialog) {
            NormalDialog(
                title = dialogTitle,
                content = {
                    Text(text = dialogMessage)
                },
                onDismiss = { showDialog = false },
                onConfirm = { permissionsState.launchMultiplePermissionRequest() }
            )
        }

        if (permissionsState.allPermissionsGranted) {
            content(
                PermissionState.Granted
            )
        } else {
            if (permissionsState.shouldShowRationale) {
                content(
                    PermissionState.NotGranted {
                        showDialog = true
                    }
                )
            } else {
                content(
                    PermissionState.NotGranted {
                        permissionsState.launchMultiplePermissionRequest()
                    }
                )
            }
        }

        SnackbarHost(hostState = snackBarHostState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .padding(dimens.screenPadding)
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 48.dp)
                        .clip(
                            shape = ShapeDefaults.ExtraSmall
                        )
                        .background(color = MaterialTheme.colorScheme.inverseSurface)
                        .align(Alignment.BottomCenter)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .padding(dimens.spacing5)
                ) {
                    Text(
                        text = it.visuals.message,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        textAlign = TextAlign.Start,
                    )
                    val actionLabel = it.visuals.actionLabel
                    if (actionLabel != null) {
                        Text(
                            text = actionLabel,
                            color = MaterialTheme.colorScheme.inversePrimary,
                            modifier = Modifier
                                .clickable {
                                    it.performAction()
                                },
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (autoRequestPermissionOnStart && event == Lifecycle.Event.ON_CREATE) {
                    if (permissionsState.shouldShowRationale) {
                        showDialog = true
                    } else {
                        permissionsState.launchMultiplePermissionRequest()
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
}

fun Context.getActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    error("Permissions should be called in the context of an Activity")
}

fun Context.openApplicationSettings() {
    startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
    )
}