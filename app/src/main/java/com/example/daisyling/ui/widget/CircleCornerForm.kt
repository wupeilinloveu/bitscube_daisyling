package com.example.daisyling.ui.widget

import android.graphics.*
import com.squareup.picasso.Transformation

/**
 * Created by Emily on 9/30/21
 */
class CircleCornerForm : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val widthLight = source.width
        val heightLight = source.height
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paintColor = Paint()
        paintColor.flags = Paint.ANTI_ALIAS_FLAG
        val rectF = RectF(Rect(0, 0, widthLight, heightLight))
        canvas.drawRoundRect(
            rectF,
            (widthLight / 12).toFloat(),
            (heightLight / 12).toFloat(),
            paintColor
        )
        val paintImage = Paint()
        paintImage.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        canvas.drawBitmap(source, 0f, 0f, paintImage)
        source.recycle()
        return output
    }

    override fun key(): String {
        return "roundcorner"
    }
}