package life.catchyour.base.app

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.ExifOrientationPolicy
import coil.disk.DiskCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import life.catchyour.BuildConfig

class AndroidBaseApplication : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

//        Places.initialize(this, this.getMapsKey())
//        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST) {}
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .respectCacheHeaders(false)
            .bitmapFactoryExifOrientationPolicy(ExifOrientationPolicy.RESPECT_ALL)
            .diskCache {
                DiskCache.Builder()
                    .directory(filesDir)
                    .maxSizePercent(1.0)
                    .build()
            }
            .logger(
                if (BuildConfig.DEBUG) DebugLogger(Log.VERBOSE) else null
            )
            .build()
    }
}
