package com.raj.sharephoto.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfileUpdateRequest(
    @Expose
    @SerializedName("name")
    var name: String,

    @Expose
    @SerializedName("profilePicUrl")
    var profilePicUrl: String,
    @Expose
    @SerializedName("tagline")
    var tagline: String
)