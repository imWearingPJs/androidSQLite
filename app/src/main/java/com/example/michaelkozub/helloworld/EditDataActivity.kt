package com.tabian.saveanddisplaysql

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.michaelkozub.helloworld.R


@SuppressLint("Registered")
/**
 * Created by User on 2/28/2017.
 */

class EditDataActivity : AppCompatActivity() {

    private var btnSave: Button? = null
    private var btnDelete: Button? = null
    private var editable_item: EditText? = null

    internal var mDatabaseHelper: DatabaseHelper? = null

    private var selectedName: String? = null
    private var selectedID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_data_layout)
        btnSave = findViewById<Button>(R.id.btnSave) as Button
        btnDelete = findViewById<Button>(R.id.btnDelete) as Button
        editable_item = findViewById<Button>(R.id.editable_item) as EditText
        mDatabaseHelper = DatabaseHelper(this)

        //get the intent extra from the ListDataActivity
        val receivedIntent = intent

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id", -1) //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name")

        //set the text to show the current selected name
        editable_item!!.setText(selectedName)

        btnSave!!.setOnClickListener {
            val item = editable_item!!.text.toString()
            if (item != "") {
                mDatabaseHelper!!.updateName(item, selectedID, selectedName!!)
            } else {
                toastMessage("You must enter a name")
            }
        }

        btnDelete!!.setOnClickListener {
            mDatabaseHelper!!.deleteName(selectedID, selectedName!!)
            editable_item!!.setText("")
            toastMessage("removed from database")
        }

    }

    /**
     * customizable toast
     * @param message
     */
    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private val TAG = "EditDataActivity"
    }
}