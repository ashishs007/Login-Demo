package com.login.api.request

import com.google.gson.annotations.SerializedName
import com.login.utils.Constants

data class VerifyOTPRequest(

    @SerializedName("mobile")
    var mobile: String = "",

    @SerializedName("otp")
    var otp: String = "",

    @SerializedName("countryCode")
    var countryCode: String = Constants.countryCode,

    @SerializedName("deviceToken")
    var deviceToken: String = ""
)