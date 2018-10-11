package com.example.michaelkozub.helloworld

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.tabian.saveanddisplaysql.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*
import com.example.michaelkozub.helloworld.R.id.editText
import com.example.michaelkozub.helloworld.R.id.btnAdd
import com.tabian.saveanddisplaysql.ListDataActivity




const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    var mDatabaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText = findViewById<EditText>(R.id.editText)
        val btnAdd = findViewById<View>(R.id.btnAdd)
        val btnView = findViewById<View>(R.id.btnView)
        mDatabaseHelper = DatabaseHelper(this)

        btnAdd.setOnClickListener {
            val firstName = editText.text.toString()
            val lastName = editText2.text.toString()
            val phoneNumber = editText3.text.toString()

            if (editText3.length() != 0) {
                //make query. If query returns false, add data.
                Log.d(TAG, "kozub phoneNumber is: " + phoneNumber)
                val doesIdExist = mDatabaseHelper!!.doesPhoneNumberExist(phoneNumber)
                Log.d(TAG, "kozub doesIdExist: " + doesIdExist)
                if (doesIdExist == false) {
                    AddData(firstName, lastName, phoneNumber)
                }  else {
                    toastMessage("The phone number already exists!")
                }
                //below clears the text field when you come back to it :)
                //editText.setText("")
            } else {
                toastMessage("You must put something in the phone number field!")
            }
        }

        btnView.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, ListDataActivity::class.java)
            startActivity(intent)
        })

    }

    fun AddData(firstName: String, lastName: String, phoneNumber: String) {
        val insertData = mDatabaseHelper!!.addData(firstName, lastName, phoneNumber)

        if (insertData) {
            toastMessage("Data Successfully Inserted!")
        } else {
            toastMessage("Something went wrong")
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
