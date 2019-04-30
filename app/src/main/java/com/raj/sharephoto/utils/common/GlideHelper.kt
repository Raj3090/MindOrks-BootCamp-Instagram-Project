package com.raj.sharephoto.utils.common

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.transition.Transition
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.SimpleTarget
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object GlideHelper {

    fun getProtectedUrl(url: String, headers: Map<String, String>): GlideUrl {
        val builder = LazyHeaders.Builder()
        for (entry in headers) builder.addHeader(entry.key, entry.value)
        return GlideUrl(url, builder.build())
    }

}