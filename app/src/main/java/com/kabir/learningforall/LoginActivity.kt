package com.kabir.learningforall

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputLayout
    private lateinit var passwordEditText: TextInputLayout
    private lateinit var loginButton: ImageButton
    private lateinit var forgotPasswordText: TextView
    private lateinit var signUpLink: TextView
    private lateinit var googleSignInButton: MaterialButton
    private lateinit var facebookSignInButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpViews()
        setUpClickListeners()
    }

    private fun setUpViews() {
        backButton = findViewById<ImageButton>(R.id.backButton)
        emailInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        emailEditText = findViewById<TextInputLayout>(R.id.emailEditText)
        passwordEditText = findViewById<TextInputLayout>(R.id.passwordEditText)
        loginButton = findViewById<ImageButton>(R.id.loginButton)
        forgotPasswordText = findViewById<TextView>(R.id.forgotPasswordText)
        signUpLink = findViewById<TextView>(R.id.signUpLink)
        googleSignInButton = findViewById<MaterialButton>(R.id.googleSignInButton)
        facebookSignInButton = findViewById<MaterialButton>(R.id.facebookSignInButton)
    }

    private fun setUpClickListeners() {
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        loginButton.setOnClickListener {
            handleLogin()
        }
        forgotPasswordText.setOnClickListener {
            handleForgotPassword()
        }
        signUpLink.setOnClickListener {
            goToSignUp()
        }
    }

    private fun handleLogin() {

    }

    private fun handleForgotPassword() {

    }

    private fun goToSignUp() {
        val intent:Intent? = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()

    }

}