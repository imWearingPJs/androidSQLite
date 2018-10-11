package com.example.michaelkozub.helloworld

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import junit.runner.BaseTestRunner.savePreferences
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast




class DisplayMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = message
        }
    }



    override fun onBackPressed() {
        //super.onBackPressed()
        //MainActivity()
    }

}
