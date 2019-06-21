package com.argeslab.tutolapplication.views.login


import com.parse.ParseUser

interface ContractLogin {
    interface View {
        fun startSplashScreen()
        fun startMainScreen()
        fun errorDialog()
        fun showProgress(show: Boolean)
    }

    interface Presenter {
        fun registerNewUser(user: ParseUser)
        fun loginUser(user: String, password: String)
    }
}