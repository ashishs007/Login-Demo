package com.login.myviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.login.LoginDemo
import com.login.api.request.LoginRequest
import com.login.api.request.SignUpRequest
import com.login.api.request.VerifyOTPRequest
import com.login.api.response.BaseResponse
import com.login.api.response.Resource
import com.login.api.response.Status
import com.login.data.model.UserDetailModel
import com.login.data.repo.UserRepository
import com.login.utils.Constants


class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: UserRepository = UserRepository(
        getApplication<LoginDemo>().getAPIService(),
        getApplication<LoginDemo>().getRetrofit()
    )
    private val signupLiveData: MediatorLiveData<Resource<BaseResponse>> = MediatorLiveData()
    private val loginLiveData: MediatorLiveData<Resource<UserDetailModel>> = MediatorLiveData()
    private val verifyOTPLiveData: MediatorLiveData<Resource<UserDetailModel>> = MediatorLiveData()

    init {
        signupLiveData.value = Resource(Status.IDLE, null, "IDLE")
        loginLiveData.value = Resource(Status.IDLE, null, "IDLE")
        verifyOTPLiveData.value = Resource(Status.IDLE, null, "IDLE")

    }

    /**
     * Register user request
     */
    fun registerUserRequest(request: SignUpRequest) {
        signupLiveData.addSource(
            repo.registerUser(
                Constants.englishCode, request
            )
        ) { t: Resource<BaseResponse>? ->
            signupLiveData.postValue(t)
        }
    }

    /**
     * Register user remote source observer
     */
    fun getRegisterUserObserver(): MediatorLiveData<Resource<BaseResponse>> {
        return signupLiveData
    }

    /**
     * Login user request
     */
    fun loginRequest(request: LoginRequest) {
        loginLiveData.addSource(
            repo.loginUser(
                Constants.englishCode,
                request
            )
        ) { t: Resource<UserDetailModel>? ->
            loginLiveData.postValue(t)
        }
    }

    /**
     * Login user remote source observer
     */
    fun getLoginUserObserver(): MediatorLiveData<Resource<UserDetailModel>> {
        return loginLiveData
    }

    /**
     * Verify user OTP request
     */
    fun verifyOTPRequest(request: VerifyOTPRequest, verifyType: String) {
        verifyOTPLiveData.addSource(
            repo.verifyOTP(
                Constants.englishCode,
                request,
                verifyType
            )
        ) { t: Resource<UserDetailModel>? ->
            verifyOTPLiveData.postValue(t)
        }
    }

    /**
     * Verify user OTP remote source observer
     */
    fun getVerifyOTPObserver(): MediatorLiveData<Resource<UserDetailModel>> {
        return verifyOTPLiveData
    }

}