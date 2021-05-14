package com.login.api.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("success")
    @Expose
    private var status: Boolean? = false

    @SerializedName("code")
    @Expose
    private var code: String? = ""

    @SerializedName("message")
    @Expose
    private var message: String? = ""

    inner class Meta {
        @SerializedName("has_update")
        @Expose
        var hasUpdate: String? = null

        @SerializedName("force_update")
        @Expose
        var forceUpdate: String? = null

        @SerializedName("version")
        @Expose
        var version: String? = null
    }

    fun getMsg(): String? {
        return message
    }
}
