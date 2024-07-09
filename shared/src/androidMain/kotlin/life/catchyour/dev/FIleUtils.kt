package life.catchyour.dev

import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

fun InputStream.copyTo(file: File) =
    try {
        file.writeBytes(readBytes())
        file
    } catch (fileNotFound: FileNotFoundException) {
        throw fileNotFound
    } finally {
        close()
    }

fun createFileInDir(dir: File, fileName: String): File = File(dir, fileName)
