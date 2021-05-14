package com.login.api.request


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.login.BR
import com.login.utils.Constants

data class SignUpRequest(

    @SerializedName("fullName")
    var fullName: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("countryCode")
    var countryCode: String = Constants.countryCode,
    @SerializedName("mobile")
    var mobile: String = "",
    @SerializedName("deviceToken")
    var deviceToken: String = ""


) : BaseObservable() {

    @Bindable
    @SerializedName("dob")
    var dob: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dob)
        }

    @Bindable
    @SerializedName("occupation")
    var occupation: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.occupation)
        }

    @Bindable
    @SerializedName("gender")
    var gender: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.gender)
        }

}