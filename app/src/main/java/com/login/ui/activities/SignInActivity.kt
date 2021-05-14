package com.login.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.login.R
import com.login.api.request.LoginRequest
import com.login.api.response.Status
import com.login.base.BaseActivity
import com.login.databinding.ActivitySignInBinding
import com.login.myviewmodel.UserViewModel


class SignInActivity : BaseActivity(), View.OnClickListener {

    lateinit var mBinding: ActivitySignInBinding
    lateinit var viewModel: UserViewModel
    val request = LoginRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        mBinding.clickListener = this
        mBinding.item = request

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)


        loginObserver()
    }

    override fun onClick(v: View?) {

        if (v?.id == R.id.tv_register_now) {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }

        if (v?.id == R.id.bt_sign_in) {
            if (validate()) {
                viewModel.loginRequest(request)
            }
        }

    }

    /**
     * Observe user login response
     */
    private fun loginObserver() {
        viewModel.getLoginUserObserver().observe(this, Observer { t ->
            when (t.status) {
                Status.LOADING -> {
                    enableLoadingBar(true)
                }
                Status.SUCCESS -> {
                    enableLoadingBar(false)
                    startActivity(
                        Intent(this@SignInActivity, OTPActivity::class.java).putExtra(
                            OTPActivity.MOBILE_NO_EXTRA, t?.data?.mobile!!
                        )
                    )

                }
                Status.ERROR -> {
                    enableLoadingBar(false)
                    onInfo(t.message!!)
                }
            }
        })

    }

    /**
     * validate user input fields for login request
     */
    private fun validate(): Boolean {
        if (request.mobile.isEmpty()) {
            onInfo(getString(R.string.please_enter_mobile_number))
            return false
        }

        if (request.mobile.length < 10) {
            onInfo(getString(R.string.enter_valid_mobile_number))
            return false
        }
        return true
    }

}