package mohsin.reza.movieapp

import java.io.IOException
import java.io.InputStreamReader

object FileReader {
    fun readStringFromFile(fileName: String): String {
        try {
            val inputStream = App.app.assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}