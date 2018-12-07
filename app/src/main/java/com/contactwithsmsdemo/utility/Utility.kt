package com.contactwithsmsdemo.utility

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*
import java.util.Arrays.asList


public class Utility {

    fun generateCircleBitmap(context: Context, circleColor: Int, diameterDP: Float, text: String?): Bitmap {
        val textColor = -0x1

        val metrics = Resources.getSystem().getDisplayMetrics()
        val diameterPixels = diameterDP * (metrics.densityDpi / 160f)
        val radiusPixels = diameterPixels / 2

        // Create the bitmap
        val output = Bitmap.createBitmap(diameterPixels.toInt(), diameterPixels.toInt(),
                Bitmap.Config.ARGB_8888)

        // Create the canvas to draw on
        val canvas = Canvas(output)
        canvas.drawARGB(0, 0, 0, 0)

        // Draw the circle
        val paintC = Paint()
        paintC.setAntiAlias(true)
        paintC.setColor(circleColor)
        canvas.drawCircle(radiusPixels, radiusPixels, radiusPixels, paintC)

        // Draw the text
        if (text != null && text.length > 0) {
            val paintT = Paint()
            paintT.setColor(textColor)
            paintT.setAntiAlias(true)
            paintT.setTextSize(radiusPixels * 2)
            val typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf")
            paintT.setTypeface(typeFace)
            val textBounds = Rect()
            paintT.getTextBounds(text, 0, text.length, textBounds)
            canvas.drawText(text, radiusPixels - textBounds.exactCenterX(), radiusPixels - textBounds.exactCenterY(), paintT)
        }

        return output
    }

    private val materialColors = Arrays.asList(
            -0xffe57373,
            -0xfff06292,
            -0xffba68c8,
            -0xff9575cd,
            -0xff7986cb,
            -0xff64b5f6,
            -0xff4fc3f7,
            -0xff4dd0e1,
            -0xff4db6ac,
            -0xff81c784,
            -0xffaed581,
            -0xffff8a65,
            -0xffd4e157,
            -0xffffd54f,
            -0xffffb74d,
            -0xffa1887f,
            -0xff90a4ae
    )

    fun getMaterialColor(key: Any): Int {
        return materialColors.get(Math.abs(key.hashCode()) % materialColors.size).toInt()
    }

    fun getTimeStamp(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date()).toString()
        //new Date().getTime());
    }

}