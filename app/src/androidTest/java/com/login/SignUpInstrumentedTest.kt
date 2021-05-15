package com.login

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.login.api.request.SignUpRequest
import com.login.api.response.Status
import com.login.myviewmodel.UserViewModel
import com.login.utils.Constants
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpInstrumentedTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    //static language English
    private val langCode = Constants.englishCode

    @Test
    fun response200_registerUser_shouldReturnSuccess() {
        val application = ApplicationProvider.getApplicationContext() as Application
        val viewModel = UserViewModel(application)
        val result = viewModel.repo.registerUser(
            langCode,
            SignUpRequest(
                "name",
                "",
                Constants.countryCode,
                "0000901234",
                Constants.staticToken
            ).apply {
                dob = "09-Jul-2021"
                gender = "Male"
                occupation = "developer"
            }
        )
        if (result.value?.status != Status.LOADING && result.value?.status != Status.IDLE)
            assert(result.value?.status == Status.SUCCESS)

    }


    @Test
    fun responseError_registerUser_shouldReturnError() {
        val application = ApplicationProvider.getApplicationContext() as Application
        val viewModel = UserViewModel(application)
        val result = viewModel.repo.registerUser(
            langCode,
            SignUpRequest()
        )
        if (result.value?.status != Status.LOADING && result.value?.status != Status.IDLE)
            assert(result.value?.status == Status.ERROR)

    }
}