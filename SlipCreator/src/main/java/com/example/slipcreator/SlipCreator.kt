package com.example.slipcreator

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.util.Log
import androidx.annotation.DrawableRes

public data class TextObject(
    val text:String,
    val fontSize:Float,
    val color: Int,
    val x: Float,
    val y: Float)

public class SlipCreator {

    public fun createSlip(
        resources: Resources,
        @DrawableRes res: Int,
        width: Int,
        height: Int,
        textDraw: List<TextObject>
    ): Bitmap{

        // 1. Source
        val source = ImageDecoder.createSource(resources, res)

        // 2. Decode
        val bitmap = ImageDecoder.decodeBitmap(source){ imageDecoder, imageInfo, source ->

            imageDecoder.setTargetSize(width, height)

            imageDecoder.setPostProcessor {  canvas->
                for(item in textDraw){
                    val text = item.text
                    val paint = Paint()
                    paint.color = item.color
                    paint.textSize = item.fontSize
                    paint.isAntiAlias = true
                    //val bound = Rect()
                    //paint.getTextBounds(text, 0, text.length, bound)
                    val x = item.x
                    val y = item.y
                    canvas.drawText(text, x, y, paint)
                }

                PixelFormat.OPAQUE
            }

        }

        return bitmap
    }

}