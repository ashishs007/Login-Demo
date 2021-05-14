package com.login.data.model

import com.google.gson.annotations.SerializedName

data class UserProfileModel(

    @SerializedName("_id")
    val _id: String,

    @SerializedName("profilePicture")
    val profilePicture: String,

    @SerializedName("fullName")
    val fullName: String,

    @SerializedName("location")
    val location: List<String>,

    @SerializedName("email")
    val email: String,

    @SerializedName("mobile")
    val mobile: String,

    @SerializedName("formattedMobile")
    val formattedMobile: String,

    @SerializedName("dob")
    val dob: String,

    @SerializedName("language")
    val language: String,

    @SerializedName("gender")
    var gender: String = "",

    @SerializedName("occupation")
    val occupation: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("notification")
    var notification: Boolean = false,

    @SerializedName("catNotifications")
    var catNotifications: ArrayList<String> = ArrayList()
)