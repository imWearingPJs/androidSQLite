package com.tabian.saveanddisplaysql

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.michaelkozub.helloworld.R

import java.util.ArrayList

/**
 * Created by User on 2/28/2017.
 */

class ListDataActivity : AppCompatActivity() {

    var mDatabaseHelper: DatabaseHelper? = null

    var mListView: ListView? = null
    var mListView2: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_layout)
        mListView = findViewById<ListView>(R.id.listView) as ListView
        mDatabaseHelper = DatabaseHelper(this)

        populateListView()
    }

    private fun populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.")

        //get the data and append to a list
        val data = mDatabaseHelper!!.data
        val listData = ArrayList<String>()
        //kozub adding below
        val listDataId = ArrayList<String>()
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            //kozub adding single line below
            listDataId.add(data.getString(3))
            listData.add(data.getString(0) + " " + data.getString(1) + " " + data.getString(2) + " " + data.getString(3))
        }
        //create the list adapter and set the adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listData)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, listDataId)
        mListView!!.adapter = adapter

        //set an onItemClickListener to the ListView
        mListView!!.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val name = adapterView.getItemAtPosition(i).toString()
            Log.d(TAG, "kozub i " + i)
            Log.d(TAG, "onItemClick: You Clicked on " + name)
            Log.d(TAG, "kozub listDataId " + listDataId.get(i))
            Log.d(TAG, "kozub listData " + listData.get(i))

            //val data = mDatabaseHelper!!.getItemID(name) //get the id associated with that name
            val data = mDatabaseHelper!!.getItemID(listDataId.get(i)) //get the id associated with that name
            var itemID = -1
            while (data.moveToNext()) {
                itemID = data.getInt(0)
                //Log.d(TAG, "data.getInt(0): " + data.getInt(0))
            }
            if (itemID > -1) {
                Log.d(TAG, "onItemClick: The ID is: " + itemID)
                val editScreenIntent = Intent(this@ListDataActivity, EditDataActivity::class.java)
                editScreenIntent.putExtra("id", itemID)
                editScreenIntent.putExtra("name", name)
                startActivity(editScreenIntent)
            } else {
                toastMessage("No ID associated with that name");
                Log.d(TAG, "onItemClick: The ID is: " + itemID)
            }
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

        private val TAG = "ListDataActivity"
    }
}