package com.raj.sharephoto.utils.display

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File


object ScreenUtils {

    fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

    fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels


     fun getDropboxIMGSize(file: File) :Pair<Int,Int>{
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.getAbsolutePath(), options)
        val imageHeight = options.outHeight
        val imageWidth = options.outWidth
        return Pair(imageHeight,imageWidth)
    }

}