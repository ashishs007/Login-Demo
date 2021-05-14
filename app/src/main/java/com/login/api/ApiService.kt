package com.login.api

import com.login.api.request.LoginRequest
import com.login.api.request.SignUpRequest
import com.login.api.request.VerifyOTPRequest
import com.login.api.response.BaseResponse
import com.login.api.response.JsonObjectResponse
import com.login.data.model.UserDetailModel
import com.login.utils.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    //Request endpoint for sign up user
    @POST("/register")
    fun registerUser(
        @Header(Constants.languageHeader) language: String,
        @Body request: SignUpRequest
    ): Call<JsonObjectResponse<BaseResponse>>

    //Request endpoint for login user
    @POST("/login")
    fun loginUser(
        @Header(Constants.languageHeader) language: String,
        @Body request: LoginRequest
    ): Call<JsonObjectResponse<UserDetailModel>>

    //Request endpoint to verify user entered OTP
    @POST("/verify-otp")
    fun verifyOTP(
        @Header(Constants.languageHeader) language: String,
        @Body request: VerifyOTPRequest,
        @Query("verifyType") verifyType: String
    ): Call<JsonObjectResponse<UserDetailModel>>
}