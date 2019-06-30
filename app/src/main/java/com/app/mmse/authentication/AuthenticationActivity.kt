package com.app.mmse.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        //Handle Registration and Login
        handleRegistration()
        handleLogin()
    }

    //Handle Registration Process with Username and Password
    private fun handleRegistration() {
        authentication_register_btn.setOnClickListener {
            RegisterUtils().register(this, this, getUsername(), getPassword()) }
    }

    //Handle Login Process with Username and Password
    private fun handleLogin() {
        authentication_login_btn.setOnClickListener {
            LoginUtils().login(this, this, getUsername(), getPassword()) }
    }

    //Get Username and Password
    private fun getUsername() = authentication_username_edittext.text.toString()
    private fun getPassword() = authentication_password_edittext.text.toString()

}
