package com.login.api.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("mobile")
    var mobile: String = ""
)