package life.catchyour.dev.core.db

import kotlin.jvm.JvmInline

@JvmInline
value class WrappedBoolean(val value: Boolean)

fun Boolean.wrap() = WrappedBoolean(this)
fun WrappedBoolean.unwrap() = value