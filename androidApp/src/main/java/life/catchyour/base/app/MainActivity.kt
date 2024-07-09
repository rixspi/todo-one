package life.catchyour.base.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import life.catchyour.BuildConfig


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         *  Useful to keep screen on while ADB Wifi
         */
        if (BuildConfig.DEBUG) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }


        installSplashScreen()

        setContent {
            AndroidBaseComposeApp(this)
        }
    }
}

