package com.login

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.login.api.request.VerifyOTPRequest
import com.login.api.response.Status
import com.login.myviewmodel.UserViewModel
import com.login.utils.Constants
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VerifyOTPInstrumentedTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    //static language English
    private val langCode = Constants.englishCode

    @Test
    fun response200_verifyOTP_shouldReturnSuccess() {
        val application = ApplicationProvider.getApplicationContext() as Application
        val viewModel = UserViewModel(application)
        val result = viewModel.repo.verifyOTP(
            langCode,
            VerifyOTPRequest("9999999999", "1234", Constants.countryCode, Constants.staticToken), ""
        )
        if (result.value?.status != Status.LOADING && result.value?.status != Status.IDLE)
            assert(result.value?.status == Status.SUCCESS)

    }


    @Test
    fun responseError_verifyOTP_shouldReturnError() {
        val application = ApplicationProvider.getApplicationContext() as Application
        val viewModel = UserViewModel(application)
        val result = viewModel.repo.verifyOTP(
            langCode,
            VerifyOTPRequest("9999999999", "incorrect_otp", Constants.countryCode, Constants.staticToken), ""
        )
        if (result.value?.status != Status.LOADING && result.value?.status != Status.IDLE)
            assert(result.value?.status == Status.ERROR)

    }

}