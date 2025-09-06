package com.kabir.learningforall

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import android.widget.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainHeading: TextView
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpButtons()
        setUpClickListeners()
        animateViews()
    }

    override fun onResume(){
        super.onResume()
        Toast.makeText(this,"On resume called", Toast.LENGTH_SHORT).show()
    }

    private fun setUpButtons(){
        mainHeading = findViewById<TextView>(R.id.mainHeading)
        signInButton = findViewById<Button>(R.id.signInBtn)
        signUpButton = findViewById<Button>(R.id.signUpBtn)
    }

    private fun setUpClickListeners(){
        signInButton.setOnClickListener {
            animateButtonClick(it) {
                val intent: Intent? = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        signUpButton.setOnClickListener {
            animateButtonClick(it) {
                val intent: Intent? = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun animateViews() {
        val slideInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)

        findViewById<View>(R.id.topSection).startAnimation(fadeInAnimation)
        findViewById<View>(R.id.centerIllustration).startAnimation(slideInAnimation)
        findViewById<View>(R.id.buttonContainer).startAnimation(fadeInAnimation)
    }

    private fun animateButtonClick(view: View, action: () -> Unit) {
        view.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(100)
                    .withEndAction {
                        action.invoke()
                    }
                    .start()
            }
            .start()
    }
}