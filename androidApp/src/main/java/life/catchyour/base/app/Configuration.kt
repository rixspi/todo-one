package life.catchyour.base.app

object Configuration {
    const val DEV_API_URL = "url"
    const val PROD_API_URL = "prod_url"

    object Map {
        const val MARKER_ZOOM: Float = 18f
        const val DEFAULT_MAP_ANIMATION_DURATION_MS = 300
        const val DEFAULT_MAP_ZOOM = 14f
    }
}

object Dates {
    const val DAY_IN_SECONDS: Long = 60 * 60 * 24
}