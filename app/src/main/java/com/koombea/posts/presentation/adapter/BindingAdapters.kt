package com.koombea.posts.presentation.adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.koombea.posts.utils.formatDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("visible")
fun View.bindVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("visibleImagen")
fun View.bindVisibleImage(url: String?) {
    visibility = if (url == null) View.GONE else View.VISIBLE
}

@BindingAdapter("url")
fun ImageView.loadUrl(url: String){
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("imgBackground")
fun ImageView.loadSrc(url: String){
    loadSrc(url)
}

@BindingAdapter("date")
fun TextView.chageFormat(date: String){
    val dateFormat_yyyyMMddHHmmss = SimpleDateFormat(
        "yyyy", Locale.getDefault()
    )
    val dateF = dateFormat_yyyyMMddHHmmss.parse("2019-10-02")

    this.setText("Oct 24th")
}