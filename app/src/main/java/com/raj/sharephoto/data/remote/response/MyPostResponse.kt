package com.raj.sharephoto.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.raj.sharephoto.data.model.ImageUrl
import com.raj.sharephoto.data.model.UploadedPostData

data class MyPostResponse(
    @Expose
    @SerializedName("statusCode")
    var statusCode: String,

    @Expose
    @SerializedName("status")
    var status: Int,

    @Expose
    @SerializedName("message")
    var message: String,


    @Expose
    @SerializedName("data")
    var data: List<UploadedPostData>



)