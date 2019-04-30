package com.raj.sharephoto.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostRequest(
    @Expose
    @SerializedName("imgUrl")
    var imgUrl: String,

    @Expose
    @SerializedName("imgWidth")
    var imgWidth: String,
    @Expose
    @SerializedName("imgHeight")
    var imgHeight: String
)