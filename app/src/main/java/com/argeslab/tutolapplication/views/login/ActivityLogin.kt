package com.argeslab.tutolapplication.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.ContentLoadingProgressBar
import com.argeslab.tutolapplication.R
import com.argeslab.tutolapplication.views.MainActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseUser

class ActivityLogin : AppCompatActivity(), View.OnClickListener, ContractLogin.View {

    private var mPresenterLogin: ContractLogin.Presenter? = null
    private lateinit var mProgressBar: ContentLoadingProgressBar
    private lateinit var mUserText: TextInputEditText
    private lateinit var mMailText: TextInputEditText
    private lateinit var mPasswordText: TextInputEditText
    private lateinit var mButtonRegister: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPresenterLogin = PresenterLogin(this)
        initViews()
    }

    /**
     * Views initialization
     * */
    private fun initViews() {
        mProgressBar = findViewById(R.id.clpb_login_progress)
        mUserText = findViewById(R.id.tiet_login_username)
        mMailText = findViewById(R.id.tiet_login_email)
        mPasswordText = findViewById(R.id.tiet_login_password)
        mButtonRegister = findViewById(R.id.efab_login_register)

        mButtonRegister.setOnClickListener(this)
    }

    /**
     * On click listener method for multiple views
     * */
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.efab_login_register -> {
                verifyLoginData()
            }
        }
    }

    /**
     * Function to validate text input data
     * */
    private fun verifyLoginData() {
        mUserText.error = null
        mMailText.error = null
        mPasswordText.error = null

        val userName = mUserText.text.toString()
        val mail = mMailText.text.toString()
        val password = mPasswordText.text.toString()

        when {
            TextUtils.isEmpty(userName) -> mUserText.error = "Dato faltante"
            TextUtils.isEmpty(mail) -> mMailText.error = "Dato faltante"
            TextUtils.isEmpty(password) -> mPasswordText.error = "Dato faltante"
            else -> {
                //presenter que verifique datos en Parse
                val newUser = ParseUser()

                newUser.email = mail
                newUser.username = userName
                newUser.setPassword(password)

                mPresenterLogin?.registerNewUser(newUser)
            }
        }

    }

    override fun startSplashScreen() {

    }

    /**
     * Function to start Main activity
     * */
    override fun startMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun errorDialog() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("Error")
            .setMessage("Ha ocurrido un error inesperado. Intentelo mas tarde.")
            .setPositiveButton("Entendido") { _, _ ->
            }
            .show()
    }

    private fun backDialog() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("Salir")
            .setMessage("Seguro que desea salir de la aplicacion.")
            .setPositiveButton("Salir") { _, _ ->
                val homeIntent = Intent(Intent.ACTION_MAIN)
                homeIntent.addCategory(Intent.CATEGORY_HOME)
                homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(homeIntent)
                android.os.Process.killProcess(android.os.Process.myPid())
            }
            .setNeutralButton("Cancelar") { _, _ ->
            }
            .show()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            mProgressBar.visibility = View.VISIBLE
            mProgressBar.bringToFront()
        } else {
            mProgressBar.visibility = View.GONE
        }
    }

    /**
     * Override on back method to prevent go back to splash screen
     * */
    override fun onBackPressed() {
        backDialog()
    }
}
