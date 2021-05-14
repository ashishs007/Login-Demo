package com.login.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.login.R
import com.login.api.request.SignUpRequest
import com.login.api.response.Status
import com.login.base.BaseActivity
import com.login.databinding.ActivitySignUpBinding
import com.login.myviewmodel.UserViewModel
import com.login.utils.Constants
import com.login.utils.StringUtils


class SignUpActivity : BaseActivity(), View.OnClickListener {

    lateinit var viewModel: UserViewModel
    lateinit var mBinding: ActivitySignUpBinding
    val request = SignUpRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        mBinding.clickListener = this
        mBinding.item = request

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        signUpObserver()

    }

    /**
     * Observe registration response
     */
    private fun signUpObserver() {
        viewModel.getRegisterUserObserver().observe(this, Observer { t ->
            when (t.status) {
                Status.LOADING -> {
                    enableLoadingBar(true)
                }
                Status.SUCCESS -> {
                    enableLoadingBar(false)
                    startActivity(
                        Intent(this@SignUpActivity, OTPActivity::class.java).putExtra(
                            OTPActivity.MOBILE_NO_EXTRA, request.mobile
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


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.bt_sign_up -> {
                if (validate()) {
                    request.apply {
                        deviceToken = Constants.staticToken
                    }
                    viewModel.registerUserRequest(request)
                }
            }
            R.id.et_date_of_birth -> {
                openDateDialog()
            }
            R.id.et_occupation -> {
                dropDownDialog(
                    getString(R.string.select_occupation),
                    resources.getStringArray(R.array.occupation),
                    R.id.et_occupation
                )
            }
            R.id.et_gender -> {
                dropDownDialog(
                    getString(R.string.select_gender),
                    resources.getStringArray(R.array.gender),
                    R.id.et_gender
                )
            }
            R.id.tv_sign_in -> {
                startActivity(
                    Intent(this@SignUpActivity, SignInActivity::class.java).setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                )

            }
        }

    }

    /**
     * validate user input fields for sign-up request
     */
    private fun validate(): Boolean {

        if (request.fullName.isEmpty()) {
            onInfo(getString(R.string.please_enter_your_full_name))
            return false
        }


        if (request.email.isNotEmpty() && !StringUtils.validateEmail(request.email)) {
            onInfo(getString(R.string.please_enter_valid_email_address))
            return false
        }

        if (request.mobile.isEmpty()) {
            onInfo(getString(R.string.please_enter_mobile_number))
            return false
        }

        if (request.mobile.length < 10) {
            onInfo(getString(R.string.enter_valid_mobile_number))
            return false
        }

        if (request.dob.isEmpty()) {
            onInfo(getString(R.string.please_enter_your_dob))
            return false
        }

        if (request.occupation.isEmpty()) {
            onInfo(getString(R.string.please_select_your_occupation))
            return false
        }

        if (!mBinding.appCompatCheckBox.isChecked) {
            onInfo(getString(R.string.please_accept_terms_of_service_and_privacy_policy))
            return false
        }
        return true
    }

    /**
     * Date of birth call back method
     */
    override fun onDOBSelected(Dob: String) {
        request.dob = Dob
    }

    /**
     * Occupation and Gender drop down item selection call back method
     */
    override fun onDropDownItemSelected(id: Int, position: Int) {
        if (id == R.id.et_occupation) {
            request.occupation = resources.getStringArray(R.array.occupation)[position]
        } else if (id == R.id.et_gender) {
            request.gender = resources.getStringArray(R.array.gender)[position]
        }
    }
}