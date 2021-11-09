package com.example.daisyling.common.util.download

import java.io.*

/**
 * Created by Emily on 10/11/21
 */
object FileUtil {
    @JvmStatic
    fun writeFile(filePath: String?, input: InputStream?): File? {
        if (input == null) return null
        val file = File(filePath)
        try {
            FileOutputStream(file).use { fos ->
                input.use { ins ->
                    val b = ByteArray(1024)
                    var len: Int
                    while (ins.read(b).also { len = it } != -1) {
                        fos.write(b, 0, len)
                    }
                    fos.flush()
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}