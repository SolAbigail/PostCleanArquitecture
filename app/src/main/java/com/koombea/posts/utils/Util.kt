package com.koombea.posts.utils

import android.content.Context
import android.graphics.Bitmap
import android.text.TextUtils
import android.util.Log
import androidx.annotation.Nullable
import com.koombea.posts.App
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


private const val CHILD_DIR = "images"
private const val TEMP_FILE_NAME = "img"
private const val FILE_EXTENSION = ".jpg"

private const val COMPRESS_QUALITY = 100

fun formatDate(date: String): String {

    val dateFormat_yyyyMMddHHmmss = SimpleDateFormat(
        "MM dd", Locale.ENGLISH
    )
    val date = dateFormat_yyyyMMddHHmmss.parse(date)
    val calendar = Calendar.getInstance()
    calendar.setTime(date)

    return date.toString()
}

fun saveImgToCache(bitmap: Bitmap, @Nullable name: String, context: Context): File? {
    var cachePath: File? = null
    var fileName: String = TEMP_FILE_NAME
    if (!TextUtils.isEmpty(name)) {
        fileName = name
    }

    var fileExist = File(context.getCacheDir().toString() + "/" + fileName, CHILD_DIR)
    if(!fileExist.exists()){
        try {
            cachePath = File(context.getCacheDir(), CHILD_DIR)
            cachePath.mkdirs()
            val stream = FileOutputStream(cachePath.toString() + "/" + fileName + FILE_EXTENSION)
            bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESS_QUALITY, stream)
            stream.close()
        } catch (e: IOException) {
            Log.e("Error:", "saveImgToCache error: $bitmap", e)
        }
    }
    return cachePath
}

fun extratNameUrl(string: String): String{
    val list = string.split("/").toTypedArray()
    var name = list[list.size-1]

    val listPoint = name.split(".").toTypedArray()
    var nameExt = listPoint[0]
    return nameExt
}

fun getPathFile(name: String, context: Context): File{
    return  File(context.getCacheDir().toString() + "/" + name, CHILD_DIR)
}