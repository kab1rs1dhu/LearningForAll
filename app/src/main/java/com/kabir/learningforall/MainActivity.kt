package com.kabir.learningforall

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainHeading: TextView
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainHeading = findViewById<TextView>(R.id.mainHeading)
        signInButton = findViewById<Button>(R.id.signInBtn)
        signUpButton = findViewById<Button>(R.id.signUpBtn)
        signInButton.setOnClickListener {
            val intent: Intent? = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        signUpButton.setOnClickListener {
            val intent: Intent? = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}