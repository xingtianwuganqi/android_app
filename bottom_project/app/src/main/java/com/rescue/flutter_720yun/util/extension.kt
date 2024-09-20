package com.rescue.flutter_720yun.util

import com.rescue.flutter_720yun.BaseApplication

fun Int.dpToPx(): Int {
    return (this * BaseApplication.context.resources.displayMetrics.density).toInt()
}