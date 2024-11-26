package life.catchyour.base.app

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import life.catchyour.dev.core.compose.theme.AppTheme


@SuppressLint("RestrictedApi")
@Composable
fun AndroidBaseComposeApp(
    activity: ComponentActivity,
) {
    AppTheme() {
        // A surface container using the 'background' color from the theme
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            it
            Text(text = "Hello World!")
        }
    }
}

