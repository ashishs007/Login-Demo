package com.login.utils

import com.login.api.response.BaseResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException


class ErrorUtils {

    companion object {
        fun parseError(response: Response<*>, retrofit: Retrofit): BaseResponse? {
            val converter: Converter<ResponseBody, BaseResponse> = retrofit
                .responseBodyConverter(BaseResponse::class.java, arrayOfNulls<Annotation>(0))
            val error: BaseResponse
            error = try {
                converter.convert(response.errorBody()!!)!!
            } catch (e: IOException) {
                return null
            }
            return error
        }

    }

}