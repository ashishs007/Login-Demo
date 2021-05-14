package com.login.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.login.R
import com.login.api.request.LoginRequest
import com.login.api.request.VerifyOTPRequest
import com.login.api.response.Status
import com.login.base.BaseActivity
import com.login.databinding.ActivityOtpBinding
import com.login.myviewmodel.UserViewModel
import com.login.utils.Constants
import com.login.utils.StringUtils


class OTPActivity : BaseActivity(), View.OnClickListener {

    lateinit var mBinding: ActivityOtpBinding
    lateinit var viewModel: UserViewModel
    val request = VerifyOTPRequest()


    companion object {
        const val MOBILE_NO_EXTRA = "mobile_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp)
        mBinding.clickListener = this

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        otpObserver()
        init()
    }

    /**
     * Initialize toolbar and listener
     */
    private fun init() {
        configureToolBar(mBinding.toolbar)
        mBinding.ivOtp1.addTextChangedListener(GenericTextWatcher(mBinding.ivOtp1))
        mBinding.ivOtp2.addTextChangedListener(GenericTextWatcher(mBinding.ivOtp2))
        mBinding.ivOtp3.addTextChangedListener(GenericTextWatcher(mBinding.ivOtp3))
        mBinding.ivOtp4.addTextChangedListener(GenericTextWatcher(mBinding.ivOtp4))
    }




    override fun onClick(v: View?) {
        if (v?.id == R.id.bt_verify_otp) {

            if (validate()) {
                if (intent.hasExtra(MOBILE_NO_EXTRA)) {
                    request.apply {
                        otp = "${mBinding.ivOtp1.text.toString()}${mBinding.ivOtp2.text.toString()}${mBinding.ivOtp3.text.toString()}${mBinding.ivOtp4.text.toString()}"
                        deviceToken = Constants.staticToken
                        mobile = intent.getStringExtra(MOBILE_NO_EXTRA)!!
                    }
                    // in case of login/sign up OTP, verify type will be empty.
                    viewModel.verifyOTPRequest(request, "")
                }
            }
        } else if (v?.id == R.id.tv_resend) {
            resendOTP()
        }
    }

    /**
     * validate user input fields for OTP verify request
     */
    private fun validate(): Boolean {
        if (!StringUtils.validateEditText(mBinding.ivOtp1)
            || !StringUtils.validateEditText(mBinding.ivOtp2)
            || !StringUtils.validateEditText(mBinding.ivOtp3)
            || !StringUtils.validateEditText(mBinding.ivOtp4)
        ) {

            onInfo(getString(R.string.enter_otp))
            return false
        }
        return true
    }

    /**
     * Observe OTP verification response
     */
    private fun otpObserver() {

        viewModel.getVerifyOTPObserver().observe(this, Observer { t ->
            when (t.status) {
                Status.LOADING -> {
                    enableLoadingBar(true)
                }
                Status.SUCCESS -> {
                    enableLoadingBar(false)
                    startActivity(
                        Intent(
                            this@OTPActivity,
                            MainActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    )
                }
                Status.ERROR -> {
                    enableLoadingBar(false)
                    onInfo(t.message!!)
                }
            }
        })



        viewModel.getLoginUserObserver().observe(this, Observer { t ->
            when (t.status) {
                Status.LOADING -> {
                    enableLoadingBar(true)
                }
                Status.SUCCESS -> {
                    enableLoadingBar(false)
                    onInfo(getString(R.string.otp_resent_successfully))
                }
                Status.ERROR -> {
                    enableLoadingBar(false)
                    onInfo(t.message!!)
                }
            }
        })
    }

    /**
     * Method to call resend OTP API
     */
    private fun resendOTP() {
        if (intent.hasExtra(MOBILE_NO_EXTRA)) {
            val resendOTPRequest = LoginRequest().apply {
                mobile = intent.getStringExtra(MOBILE_NO_EXTRA)!!
            }
            viewModel.loginRequest(resendOTPRequest)
        }

    }

    /**
     * A generic text watcher to listen for text changes in input fields
     */
    inner class GenericTextWatcher(private val view: View) : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            when (view.id) {

                R.id.ivOtp1 -> {
                    if (text.length == 1) {
                        mBinding.ivOtp2.requestFocus()
                    }
                }
                R.id.ivOtp2 -> {
                    if (text.length == 1) {
                        mBinding.ivOtp3.requestFocus()
                    } else if (text.isEmpty()) {
                        mBinding.ivOtp1.requestFocus()

                    }
                }
                R.id.ivOtp3 -> {
                    if (text.length == 1) {
                        mBinding.ivOtp4.requestFocus()
                    } else if (text.isEmpty()) {
                        mBinding.ivOtp2.requestFocus()

                    }
                }
                R.id.ivOtp4 -> {
                    if (text.isEmpty()) {
                        mBinding.ivOtp3.requestFocus()
                    }
                }
            }
        }

        override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
        override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {

        }
    }
}