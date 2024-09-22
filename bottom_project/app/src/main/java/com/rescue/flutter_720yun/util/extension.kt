package com.rescue.flutter_720yun.util

import com.rescue.flutter_720yun.BaseApplication
import com.rescue.flutter_720yun.models.HomeListModel

// Int Extension
fun Int.dpToPx(): Int {
    return (this * BaseApplication.context.resources.displayMetrics.density).toInt()
}


// String Extension
fun String.timeToStr(): String {
    val newText = this.split(".").first()
    return newText.replace("T", " ")
}

fun String.toImgUrl(): String {
    return if (this.contains("http")) {
        this
    }else{
        "http://img.rxswift.cn/${this}"
    }
}

fun HomeListModel.getImages(): List<String>? {
    return if (!this.preview_img.isNullOrEmpty()) {
        this.preview_img
    }else{
        this.imgs
    }
}