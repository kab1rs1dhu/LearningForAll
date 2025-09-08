package com.kabir.learningforall
import com.google.android.material.textfield.TextInputEditText

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var forgotPasswordText: TextView
    private lateinit var signUpLink: TextView
    private lateinit var googleSignInButton: MaterialButton
    private lateinit var facebookSignInButton: MaterialButton
    private var MINIMUM_PASSWORD_LENGTH: Int = 6

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        setUpViews()
        setUpClickListeners()
        animateViews()
    }

    private fun setUpViews() {
        backButton = findViewById<ImageButton>(R.id.backButton)
        emailInputLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        loginButton = findViewById<MaterialButton>(R.id.loginButton)
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
        googleSignInButton.setOnClickListener {

        }
        facebookSignInButton.setOnClickListener {

        }
    }

    private fun handleLogin() {
        validateInputs()
        val email: String = emailEditText.text?.toString()?.trim()!!
        val password: String = passwordEditText.text?.toString()?.trim()!!

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task->
            if(task.isSuccessful){
                val currentUser = firebaseAuth.currentUser
                if (currentUser != null) {
                    updateLastLoginTime(currentUser.uid)
                }

                loginButton.isEnabled = true
                loginButton.text = "Sign in"

                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                startDashboardActivity(currentUser)

            }
            else{
                loginButton.isEnabled = true
                loginButton.text = "Sign In"

                val errorMessage = when (task.exception?.message) {
                    "There is no user record corresponding to this identifier. The user may have been deleted." ->
                        "No account found with this email address."
                    "The password is invalid or the user does not have a password." ->
                        "Incorrect password. Please try again."
                    "The email address is badly formatted." ->
                        "Please enter a valid email address."
                    "A network error (such as timeout, interrupted connection or unreachable host) has occurred." ->
                        "Network error. Please check your connection."
                    else -> "Login failed. Please check your credentials."
                }

                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startDashboardActivity(currentUser: com.google.firebase.auth.FirebaseUser?) {
        val intent:Intent? = Intent(this, DashboardActivity::class.java)
        intent?.putExtra("name", currentUser?.displayName)
        startActivity(intent)
        finish()
    }

    private fun updateLastLoginTime(userId: String) {
        firestore.collection("users").document(userId)
            .update("lastLoginAt", com.google.firebase.Timestamp.now())
            .addOnSuccessListener {
                // Login time updated successfully
            }
            .addOnFailureListener { e ->
                // Failed to update login time, but login was successful
                // This is not critical, so we don't show an error to the user
            }
    }

    private fun handleForgotPassword() {

    }

    private fun goToSignUp() {
        val intent:Intent? = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun validateInputs() {
        val email = emailEditText.text?.toString()?.trim() ?: ""
        val password = passwordEditText.text?.toString()?.trim() ?: ""
        if (email.isEmpty()) {
            emailInputLayout.error = "Email cannot be empty"
        } else {
            emailInputLayout.error = null
        }
        if (password.isEmpty() || password.length < MINIMUM_PASSWORD_LENGTH) {
            passwordInputLayout.error = "Password cannot be empty"
        } else {
            passwordInputLayout.error = null
        }
    }


    private fun animateViews() {
        val loginCard = findViewById<View>(R.id.loginCard)
        loginCard.alpha = 0f
        loginCard.translationY = 100f

        loginCard.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(200)
            .start()
    }

}