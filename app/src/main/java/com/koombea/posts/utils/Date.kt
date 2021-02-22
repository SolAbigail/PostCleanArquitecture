package com.koombea.posts.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: String): String {

    val dateFormat_yyyyMMddHHmmss = SimpleDateFormat(
        "MM dd", Locale.ENGLISH
    )
    val date = dateFormat_yyyyMMddHHmmss.parse(date)
    val calendar = Calendar.getInstance()
    calendar.setTime(date)

    return date.toString()
}