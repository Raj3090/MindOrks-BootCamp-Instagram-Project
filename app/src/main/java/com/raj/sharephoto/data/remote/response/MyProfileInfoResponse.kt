package com.raj.sharephoto.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.raj.sharephoto.data.model.MyProfileData
import com.raj.sharephoto.data.model.Post

data class MyProfileInfoResponse(
    @Expose
    @SerializedName("statusCode")
    var statusCode: String,

    @Expose
    @SerializedName("message")
    var message: String,

    @Expose
    @SerializedName("data")
    val data: MyProfileData
)