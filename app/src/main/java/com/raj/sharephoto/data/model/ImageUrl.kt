package com.raj.sharephoto.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageUrl(
    @Expose
    @SerializedName("imageUrl")
    val imageUrl: String
)