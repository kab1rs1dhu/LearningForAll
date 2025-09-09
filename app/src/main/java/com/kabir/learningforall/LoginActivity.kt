package com.kabir.learningforall
import com.google.android.material.textfield.TextInputEditText

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    
    private val tag = "LoginActivity"

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

        val email: String = emailEditText.text?.toString()?.trim() ?: ""
        val password: String = passwordEditText.text?.toString()?.trim() ?: ""

        Log.d(tag, "Email entered: '$email'")
        Log.d(tag, "Password length: ${password.length}")
        Log.d(tag, "Password (first 3 chars): ${password}")

        // before logging in, validate inputs
        if (!validateInputs(email, password)) {
            Log.w(tag, "Input validation failed - stopping login attempt")
            return
        }

        Log.d(tag, "Input validation passed")

        // Disable button and show loading state
        loginButton.isEnabled = false
        loginButton.text = "Signing In..."

        Log.d(tag, "Starting Firebase signInWithEmailAndPassword...")

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(tag, "Firebase signInWithEmailAndPassword completed")
                Log.d(tag, "Task successful: ${task.isSuccessful}")

                if (task.isSuccessful) {
                    Log.d(tag, "✅ LOGIN SUCCESS!")
                    val currentUser = firebaseAuth.currentUser
                    Log.d(tag, "Current user UID: ${currentUser?.uid}")
                    Log.d(tag, "Current user email: ${currentUser?.email}")

                    if (currentUser != null) {
                        updateLastLoginTime(currentUser.uid)
                    }

                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startDashboardActivity(currentUser)

                } else {
                    Log.e(tag, "❌ LOGIN FAILED")
                    Log.e(tag, "Exception: ${task.exception}")
                    Log.e(tag, "Exception message: '${task.exception?.message}'")
                    Log.e(tag, "Exception type: ${task.exception?.javaClass?.simpleName}")

                    // Log the full stack trace
                    task.exception?.printStackTrace()

                    // Re-enable button and restore text
                    loginButton.isEnabled = true
                    loginButton.text = "Sign In"

                    val errorMessage = when (task.exception?.message) {
                        "There is no user record corresponding to this identifier. The user may have been deleted." -> {
                            Log.d(tag, "Error type: No user found")
                            "No account found with this email address."
                        }
                        "The password is invalid or the user does not have a password." -> {
                            Log.d(tag, "Error type: Invalid password")
                            "Incorrect password. Please try again."
                        }
                        "The email address is badly formatted." -> {
                            Log.d(tag, "Error type: Bad email format")
                            "Please enter a valid email address."
                        }
                        else -> {
                            if (task.exception?.message?.contains("network", ignoreCase = true) == true) {
                                Log.d(tag, "Error type: Network error")
                                "Network error. Please check your connection."
                            } else {
                                Log.d(tag, "Error type: Unknown - ${task.exception?.message}")
                                "Login failed: ${task.exception?.message ?: "Unknown error"}"
                            }
                        }
                    }

                    Log.d(tag, "Showing error message: $errorMessage")
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
            .addOnSuccessListener { authResult ->
                Log.d(tag, "✅ addOnSuccessListener called")
                Log.d(tag, "AuthResult user: ${authResult.user?.email}")
            }
            .addOnFailureListener { exception ->
                Log.e(tag, "❌ addOnFailureListener called")
                Log.e(tag, "Failure exception: $exception")
                Log.e(tag, "Failure message: ${exception.message}")
                exception.printStackTrace()

                loginButton.isEnabled = true
                loginButton.text = "Sign In"
                Toast.makeText(this, "Login failed: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun startDashboardActivity(currentUser: com.google.firebase.auth.FirebaseUser?) {
        Log.d(tag, "Starting dashboard activity...")
        Log.d(tag, "User: ${currentUser?.email}")

        try {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("name", currentUser?.displayName)
            Log.d(tag, "Intent created, starting activity...")
            startActivity(intent)
            Log.d(tag, "Activity started successfully")
            finish()
        } catch (e: Exception) {
            Log.e(tag, "❌ Error starting dashboard activity", e)
            Toast.makeText(this, "Error opening dashboard: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateLastLoginTime(userId: String) {
        Log.d(tag, "Updating last login time for user: $userId")
        firestore.collection("users").document(userId)
            .update("lastLoginAt", com.google.firebase.Timestamp.now())
            .addOnSuccessListener {
                Log.d(tag, "Last login time updated successfully")
            }
            .addOnFailureListener { e ->
                Log.w(tag, "Failed to update last login time", e)
            }
    }

    private fun handleForgotPassword() {
        Log.d(tag, "Forgot password clicked")
        // TODO: Implement forgot password functionality
    }

    private fun goToSignUp() {
        Log.d(tag, "Going to sign up")
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateInputs(email: String, password: String): Boolean {
        Log.d(tag, "Validating inputs...")
        var isValid = true

        if(email.isEmpty()){
            Log.d(tag, "Validation failed: Email is empty")
            isValid = false
            emailInputLayout.error = "Email cannot be empty"
        } else if(!isValidEmail(email)){
            Log.d(tag, "Validation failed: Email format invalid")
            isValid = false
            emailInputLayout.error = "Please enter a valid email address"
        } else {
            Log.d(tag, "Email validation passed")
            emailInputLayout.error = null
        }

        if (password.isEmpty()) {
            Log.d(tag, "Validation failed: Password is empty")
            passwordInputLayout.error = "Password cannot be empty"
            isValid = false
        } else if (password.length < MINIMUM_PASSWORD_LENGTH) {
            Log.d(tag, "Validation failed: Password too short (${password.length} chars)")
            passwordInputLayout.error = "Password must be at least $MINIMUM_PASSWORD_LENGTH characters"
            isValid = false
        } else {
            Log.d(tag, "Password validation passed")
            passwordInputLayout.error = null
        }

        Log.d(tag, "Validation result: $isValid")
        return isValid
    }

    private fun isValidEmail(email: String): Boolean {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        Log.d(tag, "Email '$email' is valid: $isValid")
        return isValid
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