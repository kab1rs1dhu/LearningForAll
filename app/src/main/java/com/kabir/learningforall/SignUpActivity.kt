package com.kabir.learningforall

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignUpActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var fullNameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var phoneInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var confirmPasswordInputLayout: TextInputLayout
    private lateinit var fullNameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var termsCheckbox: MaterialCheckBox
    private lateinit var termsLink: TextView
    private lateinit var signUpButton: MaterialButton
    private lateinit var signInLink: TextView
    private lateinit var googleSignUpButton: MaterialButton
    private lateinit var facebookSignUpButton: MaterialButton
    private val MINIMUM_NAME_LENGTH: Int = 2
    private val MINIMUM_PASSWORD_LENGTH: Int = 6

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        setUpViews()
        setUpClickListeners()
        animateViews()
    }

    private fun setUpViews() {
        backButton = findViewById(R.id.backButton)
        fullNameInputLayout = findViewById(R.id.fullNameInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        phoneInputLayout = findViewById(R.id.phoneInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout)
        fullNameEditText = findViewById(R.id.fullNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        termsCheckbox = findViewById(R.id.termsCheckbox)
        termsLink = findViewById(R.id.termsLink)
        signUpButton = findViewById(R.id.signUpButton)
        signInLink = findViewById(R.id.signInLink)
        googleSignUpButton = findViewById(R.id.googleSignUpButton)
        facebookSignUpButton = findViewById(R.id.facebookSignUpButton)
    }

    private fun setUpClickListeners() {
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        signInLink.setOnClickListener {
            goToSignIn()
        }
        signUpButton.setOnClickListener {
            handleSignUp()
        }
        termsCheckbox.setOnCheckedChangeListener { _, _ ->
            signUpButton.isEnabled = validateInputs()
        }

        googleSignUpButton.setOnClickListener {

        }
        facebookSignUpButton.setOnClickListener {

        }

    }

    private fun validateInputs(): Boolean {
        val fullName = fullNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        var isValid = true

        // checking the full name
        if(fullName.isEmpty() || fullName.length < MINIMUM_NAME_LENGTH){
            isValid = false
            fullNameInputLayout.error = "Name must be at least $MINIMUM_NAME_LENGTH characters"
        } else {
            fullNameInputLayout.error = null
        }

        // checking the email
        if(!email.isEmpty() && !isValidEmail(email)){
            isValid = false
            emailInputLayout.error = "Email cannot be empty"
        } else {
            emailInputLayout.error = null
        }

        // checking the phone number
        if(!phone.isEmpty() && phone.length < 10){
            isValid = false
            phoneInputLayout.error = "Phone number must be at least 10 digits"
        } else {
            phoneInputLayout.error = null
        }

        // checking the password
        if(!password.isEmpty() && password.length < MINIMUM_PASSWORD_LENGTH && !isValidPassword(password)){
            isValid = false
            passwordInputLayout.error = "Password must be at least $MINIMUM_PASSWORD_LENGTH characters"
        } else {
            passwordInputLayout.error = null
        }

        // checking if the passwords match
        if(!confirmPassword.isEmpty() && confirmPassword != password){
            isValid = false
            confirmPasswordInputLayout.error = "Passwords do not match"
        } else {
            confirmPasswordInputLayout.error = null
        }

        if(!termsCheckbox.isChecked){
            isValid = false
            termsCheckbox.error = "You must agree to the terms and conditions"
        } else {
            isValid = isValid && true
            termsCheckbox.error = null
        }

        return isValid
    }

    private fun handleSignUp() {
        val fullName = fullNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val password = passwordEditText.text.toString()

        signUpButton.isEnabled = false

        if (!validateInputs()) {
            signUpButton.isEnabled = true
            return
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        user?.let {
                            createUserDocument(it.uid, fullName, email, phone)
                            goToSignIn()
                        }
                    } else {
                        signUpButton.isEnabled = true
                        signUpButton.text = "Create Account"
                        val errorMessage = when (task.exception?.message) {
                            "The email address is already in use by another account." ->
                                "Email is already registered. Please use a different email."

                            "The email address is badly formatted." ->
                                "Please enter a valid email address."

                            "The given password is invalid. [ Password should be at least 6 characters ]" ->
                                "Password must be at least 6 characters long."

                            else -> "Registration failed. Please try again."
                        }
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                    }

                }
        }
    }

    private fun createUserDocument(userId: String, fullName: String, email: String, phone: String) {
        val userMap = hashMapOf(
            "fullName" to fullName,
            "email" to email,
            "phoneNumber" to phone,
            "createdAt" to com.google.firebase.Timestamp.now(),
            "lastLoginAt" to com.google.firebase.Timestamp.now()
        )

        firestore.collection("users").document(userId).set(userMap)
            .addOnCompleteListener {
                signUpButton.isEnabled = true
                signUpButton.text = "Create Account"
                Toast.makeText(this, "User profile created successfully!", Toast.LENGTH_LONG).show()
                // navigate to the dashboard now
            }
            .addOnFailureListener { e ->
                signUpButton.isEnabled = true
                signUpButton.text = "Create Account"
                Toast.makeText(this, "Failed to create user profile: ${e.message}", Toast.LENGTH_LONG).show()
            }

        firestore.collection("users").document(userId)
            .set(userMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show()
                goToSignIn()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to create user profile: ${e.message}", Toast.LENGTH_LONG).show()
                signUpButton.isEnabled = true
                signUpButton.text = "Create Account"
            }

    }

    private fun goToSignIn() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun animateViews() {
        val signUpCard = findViewById<View>(R.id.signUpCard)
        signUpCard.alpha = 0f
        signUpCard.translationY = 100f

        signUpCard.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(200)
            .start()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password : String): Boolean {
        // implement a valid password check. :TODO later
        return true
    }

}