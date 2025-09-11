package com.kabir.learningforall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val class1 = findViewById<androidx.cardview.widget.CardView>(R.id.class1Card)
        class1.setOnClickListener {
            val intent = android.content.Intent(this, Class1Activity::class.java)
            startActivity(intent)
        }
    }

}