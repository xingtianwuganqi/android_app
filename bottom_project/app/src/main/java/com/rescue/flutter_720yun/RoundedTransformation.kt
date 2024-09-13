package com.rescue.flutter_720yun
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.squareup.picasso.Transformation
import java.util.*

class CircleTransformation : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val radius = Math.min(width, height) / 2f

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
            color = 0xff424242.toInt()
        }
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, radius, radius, paint)

        paint.xfermode = android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(source, 0f, 0f, paint)

        source.recycle()

        return output
    }

    override fun key(): String {
        return "circleTransformation" + Date().time
    }
}