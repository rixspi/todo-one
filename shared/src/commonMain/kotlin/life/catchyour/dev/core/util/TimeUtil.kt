package life.catchyour.dev.core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDate.Companion.now(): LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date