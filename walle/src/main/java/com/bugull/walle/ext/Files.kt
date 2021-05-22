package com.bugull.walle.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.bugull.walle.action.content.logger.LogLine
import com.bugull.walle.action.content.logger.formatStr
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Author by xpl, Date on 2021/5/22.
 */

private val sExecutorService: ExecutorService = ThreadPoolExecutor(
    1,
    5,
    60L,
    TimeUnit.SECONDS,
    SynchronousQueue(),
    ThreadPoolExecutor.AbortPolicy()
)

@SuppressLint("SimpleDateFormat")
private val SDF = SimpleDateFormat("yyyyMMddHHmmss")
private fun now() = SDF.format(System.currentTimeMillis())

fun export2File(context: Context, list: List<LogLine>, success: (File) -> Unit = {
    shareFile(context,it)
}) {
    sExecutorService.execute {
        if (list.isEmpty()) {
            loge("export2File list is empty")
            return@execute
        }
        val log = list.joinToString(separator = "\n") {
            it.formatStr
        }
        val name = "${context.filesDir}${File.separator}walle_${now()}.log"
        val file: File = File(name)
        val result = createFile(file)
        if (!result) {
            loge("export2File createFile $name fail")
            return@execute
        }
        val write2File = write2File(file, log)
        if (write2File) {
            logv("export2File $name success")
            success(file)
        } else {
            loge("export2File write2File $name fail")
        }
    }
}

fun shareFile(context: Context, file: File) {
    try {
        context.startActivity(Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri: Uri = FileProvider.getUriForFile(
                context,
                context.packageName + ".fileprovider",
                file
            )
            val type = context.contentResolver.getType(uri)
            setDataAndType(uri, type)
            putExtra(Intent.EXTRA_STREAM, uri)
            if (resolveActivity(context.packageManager) == null) {
                setDataAndType(uri, "*/*")
            }
        })
    } catch (e: Throwable) {
        e.printStackTrace()
        loge("分享${file.absolutePath}失败")
    }


}

fun createDir(file: File) = if (file.exists()) file.isDirectory else file.mkdirs()

fun createFile(file: File): Boolean {
    if (file.exists()) {
        return file.isFile
    }
    val parentFile = file.parentFile ?: return false
    if (!createDir(parentFile)) return false
    return try {
        file.createNewFile()
    } catch (e: Throwable) {
        e.printStackTrace()
        false
    }
}

fun write2File(file: File, content: String, append: Boolean = true): Boolean {
    var writer: BufferedWriter? = null
    return try {
        writer = BufferedWriter(FileWriter(file, append))
        writer.write(content)
        true
    } catch (e: Throwable) {
        e.printStackTrace()
        false
    } finally {
        try {
            writer?.close()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}