package com.login.api.response

import com.google.gson.annotations.SerializedName

class JsonObjectResponse<T> : BaseResponse() {
    @SerializedName("data")
    var dataObject: T? = null
}