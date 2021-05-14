package com.login.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.login.api.ApiService
import com.login.api.request.LoginRequest
import com.login.api.request.SignUpRequest
import com.login.api.request.VerifyOTPRequest
import com.login.api.response.BaseResponse
import com.login.api.response.JsonObjectResponse
import com.login.api.response.Resource
import com.login.api.response.Status
import com.login.data.model.UserDetailModel
import com.login.utils.ErrorUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class UserRepository(var apiService: ApiService, var retrofit: Retrofit) {

    /**
     * Register user API remote data source
     */
    fun registerUser(
        language: String,
        request: SignUpRequest
    ): LiveData<Resource<BaseResponse>> {

        val data = MutableLiveData<Resource<BaseResponse>>()
        data.value = Resource(Status.LOADING, null, "")
        apiService.registerUser(language, request)
            .enqueue(object : Callback<JsonObjectResponse<BaseResponse>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<BaseResponse>>,
                    response: Response<JsonObjectResponse<BaseResponse>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        data.value = Resource(
                            Status.SUCCESS,
                            response.body()?.dataObject,
                            response.body()?.getMsg()
                        )
                    } else {
                        try {
                            val errorResponse = ErrorUtils.parseError(response, retrofit)
                            if (errorResponse != null) {
                                data.value = Resource(
                                    Status.ERROR,
                                    null,
                                    errorResponse.getMsg()
                                )
                            } else {
                                onFailure(call, Throwable())
                            }
                        } catch (ex: Exception) {
                            onFailure(call, ex)
                        }

                    }
                }

                override fun onFailure(call: Call<JsonObjectResponse<BaseResponse>>, t: Throwable) {
                    data.value = Resource(Status.ERROR, null, t.localizedMessage)
                }
            })

        return data
    }

    /**
     * Login user API remote data source
     */
    fun loginUser(
        language: String,
        request: LoginRequest
    ): LiveData<Resource<UserDetailModel>> {

        val data = MutableLiveData<Resource<UserDetailModel>>()
        data.value = Resource(Status.LOADING, null, "")
        apiService.loginUser(language, request)
            .enqueue(object : Callback<JsonObjectResponse<UserDetailModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<UserDetailModel>>,
                    response: Response<JsonObjectResponse<UserDetailModel>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        data.value = Resource(
                            Status.SUCCESS,
                            response.body()?.dataObject,
                            response.body()?.getMsg()
                        )
                    } else {
                        try {
                            val errorResponse = ErrorUtils.parseError(response, retrofit)
                            if (errorResponse != null) {
                                data.value = Resource(
                                    Status.ERROR,
                                    null,
                                    errorResponse.getMsg()
                                )
                            } else {
                                onFailure(call, Throwable())
                            }
                        } catch (ex: Exception) {
                            onFailure(call, ex)
                        }

                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<UserDetailModel>>,
                    t: Throwable
                ) {
                    data.value = Resource(Status.ERROR, null, t.localizedMessage)
                }
            })

        return data
    }

    /**
     * Verify OTP API remote data source
     */
    fun verifyOTP(
        language: String,
        request: VerifyOTPRequest, verifyType: String
    ): LiveData<Resource<UserDetailModel>> {

        val data = MutableLiveData<Resource<UserDetailModel>>()
        data.value = Resource(Status.LOADING, null, "")
        apiService.verifyOTP(language, request, verifyType)
            .enqueue(object : Callback<JsonObjectResponse<UserDetailModel>> {
                override fun onResponse(
                    call: Call<JsonObjectResponse<UserDetailModel>>,
                    response: Response<JsonObjectResponse<UserDetailModel>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        data.value = Resource(
                            Status.SUCCESS,
                            response.body()?.dataObject,
                            response.body()?.getMsg()
                        )
                    } else {

                        try {
                            val errorResponse = ErrorUtils.parseError(response, retrofit)
                            if (errorResponse != null) {
                                data.value = Resource(
                                    Status.ERROR,
                                    null,
                                    errorResponse.getMsg()
                                )
                            } else {
                                onFailure(call, Throwable())
                            }
                        } catch (ex: Exception) {
                            onFailure(call, ex)
                        }

                    }
                }

                override fun onFailure(
                    call: Call<JsonObjectResponse<UserDetailModel>>,
                    t: Throwable
                ) {
                    data.value = Resource(Status.ERROR, null, t.localizedMessage)
                }
            })

        return data
    }

}