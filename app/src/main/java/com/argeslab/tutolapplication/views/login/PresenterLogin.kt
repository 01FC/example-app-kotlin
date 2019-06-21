package com.argeslab.tutolapplication.views.login

import com.parse.ParseUser

class PresenterLogin(cView: ContractLogin.View) : ContractLogin.Presenter {
    val mView = cView

    override fun registerNewUser(user: ParseUser) {
        mView.showProgress(true)
        user.signUpInBackground { e ->
            if (e == null) {
                mView.showProgress(false)
                mView.startMainScreen()
            } else {
                mView.showProgress(false)
                mView.errorDialog()
            }
        }
    }

    override fun loginUser(user: String, password: String) {
        mView.showProgress(true)
        ParseUser.logInInBackground(user, password) { _, e ->
            if (e == null) {
                mView.showProgress(false)
                mView.startMainScreen()
            } else {
                mView.showProgress(false)
                mView.errorDialog()
            }
        }
    }

}