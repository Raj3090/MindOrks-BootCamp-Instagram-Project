package com.raj.sharephoto.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class MyProfileData(
        @Expose
        @SerializedName("id")
        val id: String,

        @Expose
        @SerializedName("name")
        val name: String,

        @Expose
        @SerializedName("tagline")
        val tagline: String,

        @Expose
        @SerializedName("profilePicUrl")
        val profilePicUrl: String?
    )
