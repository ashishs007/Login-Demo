package com.login.utils


object Constants {

    /* Base URLs*/

    //const val BASE_URL = "http://192.168.0.74:18402/" //Local server URL
    //const val BASE_URL = "http://192.168.0.158:18402/" //Local server URL
    const val BASE_URL = "http://mymeetingdesk.com:18402/" //Development  server URL

    //Image base url
    const val IMAGE_BASE_URL = "https://dev.s3.amazonaws.com/"

    /*Headers*/
    const val languageHeader = "Accept-Language"
    const val authorizationHeader = "Authorization"


    /*Miscellaneous*/
    const val FORMATE_dd_MMMM_yyyy: String = "dd MMMM yyyy"

    //for now the app will be available only in india
    const val countryCode: String = "+91"

    //static device token
    const val staticToken: String = "static_device_token"

    //static language code
    const val englishCode: String = "en"
}