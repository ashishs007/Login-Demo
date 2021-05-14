package com.login.data.model

import com.google.gson.annotations.SerializedName




data class UserDetailModel(

    @SerializedName("token")
    var token: String = "",

    @SerializedName("countryCode")
    var countryCode: String = "",

    @SerializedName("deviceToken")
    var deviceToken: String = "",

    @SerializedName("location")
    var location: List<String> = ArrayList(),

    @SerializedName("_id")
    var _id: String = "",

    @SerializedName("fullName")
    var fullName: String = "",

    @SerializedName("mobile")
    var mobile: String = "",

    @SerializedName("email")
    var email: String = "",

    @SerializedName("updatedAt")
    var updatedAt: String = ""
)