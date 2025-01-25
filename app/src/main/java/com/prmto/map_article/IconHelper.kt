package com.prmto.map_article

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.clustering.Cluster

object IconHelper {

    fun getClusterIcon(
        context: Context,
        cluster: Cluster<CarClusterItem>
    ): BitmapDescriptor {
        val bucketStr = getBucketString(cluster.size)
        val bmpClusterSize = vectorToBitmap(context, R.drawable.ic_cluster)
        val bitmap = bmpClusterSize.drawClusterCount(context, bucketStr, R.color.white)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun getCarIcon(
        item: CarClusterItem,
        context: Context
    ): BitmapDescriptor? {
        val bitmap = if (item.isSelected) {
            R.drawable.ic_selected
        } else {
            R.drawable.ic_unselected
        }
        return vectorToBitmapDescriptor(context, bitmap)
    }

    private fun getBucketString(size: Int): String {
        return when {
            size > 999 -> "1000+"
            size > 499 -> "500+"
            size > 99 -> "100+"
            size > 49 -> "50+"
            size > 19 -> "20+"
            size > 9 -> "10+"
            else -> size.toString()
        }
    }

    private fun Bitmap.drawClusterCount(context: Context, text: String, color: Int): Bitmap {
        val bitmap = copy(config!!, true)
        val canvas = Canvas(bitmap)
        val paint = createPaint(context, color)
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val width = bitmap.width / 2
        val height = bitmap.height / 2.7
        canvas.drawText(text, width.toFloat(), height.toFloat(), paint)
        return bitmap
    }

    private fun createPaint(context: Context, color: Int): Paint {
        return Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.CENTER
            this.color = ContextCompat.getColor(context, color)
            this.textSize = context.resources.getDimensionPixelSize(R.dimen.text_size_9sp).toFloat()
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        }
    }

    private fun vectorToBitmap(
        context: Context,
        @DrawableRes vectorResId: Int,
    ): Bitmap {
        val drawable = ContextCompat.getDrawable(context, vectorResId)
        val bitmap = Bitmap.createBitmap(
            drawable?.intrinsicWidth ?: 0,
            drawable?.intrinsicHeight ?: 0,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, canvas.width, canvas.height)
        drawable?.draw(canvas)
        return bitmap
    }

    private fun vectorToBitmapDescriptor(
        context: Context,
        @DrawableRes vectorResId: Int
    ): BitmapDescriptor? {
        val bitmap = vectorToBitmap(context, vectorResId)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}