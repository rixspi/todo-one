package life.catchyour.dev.core.logging

import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import org.koin.core.logger.PrintLogger

actual class KoinLogger : Logger() {
    val printLogger = PrintLogger()

    override fun display(level: Level, msg: MESSAGE) {
        printLogger.display(level, msg)
    }
}