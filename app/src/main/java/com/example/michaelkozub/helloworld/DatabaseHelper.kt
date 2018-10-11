package com.tabian.saveanddisplaysql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

/**
 * Created by User on 2/28/2017.
 */

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, TABLE_NAME, null, 1) {

    /**
     * Returns all the data from database
     * @return
     */
    val data: Cursor
        get() {
            val db = this.writableDatabase
            val query = "SELECT * FROM " + TABLE_NAME
            return db.rawQuery(query, null)
        }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "kozub onCreate")
//        val createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COL2 + " TEXT)"
        val createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT)"
        Log.d(TAG, "kozub createTable $createTable")

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        Log.d(TAG, "kozub onUpgrade")
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addData(item: String, item2: String, item3: String): Boolean {
        Log.d(TAG,"item $item")
        Log.d(TAG,"item2 $item2")
        Log.d(TAG,"item3 $item3")
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL2, item)
        contentValues.put(COL3, item2)
        contentValues.put(COL4, item3)

        Log.d(TAG,"contentValues $contentValues")
        Log.d(TAG, "addData: Adding $item to $TABLE_NAME")
        Log.d(TAG,"table $contentValues")

        val result = db.insert(TABLE_NAME, null, contentValues)

        //if date as inserted incorrectly it will return -1
        if (result == -1L) {
            return false
        } else {
            return true
        }
    }

    //this is to check if the phone number already exists in the db
    fun doesPhoneNumberExist(id: String): Boolean {
        val db = this.writableDatabase
        val selectString = "SELECT " + COL4 + " FROM " + TABLE_NAME +
                " WHERE " + COL4 + " = '" + id + "'"

        Log.d(TAG, "kozub selectString " + selectString)

        val cursor = db.rawQuery(selectString, null)
        Log.d(TAG, "kozub cursor " + cursor.getCount())
        if (cursor.getCount() > 0) {
            Log.d(TAG, "kozub cursor = 0 " + cursor.getCount())
            cursor.close()
            return true
        } else {
            cursor.close()
            return false
        }
    }
    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
//    fun getItemID(name: String): Cursor {
    fun getItemID(pNumber: String): Cursor {
        Log.d(TAG, "onItemClick: getItemID name: " + pNumber)
        val db = this.writableDatabase
        val query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL4 + " = '" + pNumber + "'"
        Log.d(TAG, "The query is: " + query)
        return db.rawQuery(query, null)
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    fun updateName(newName: String, id: Int, oldName: String) {
        val db = this.writableDatabase
        val query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'"
        Log.d(TAG, "updateName: query: " + query)
        Log.d(TAG, "updateName: Setting name to " + newName)
        db.execSQL(query)
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    fun deleteName(id: Int, name: String) {
        val db = this.writableDatabase
        val query = ("DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'")
        Log.d(TAG, "deleteName: query: " + query)
        Log.d(TAG, "deleteName: Deleting $name from database.")
        db.execSQL(query)
    }

    companion object {

        private val TAG = "DatabaseHelper"

        private val TABLE_NAME = "people_table"
        private val COL1 = "ID"
        private val COL2 = "name"
        private val COL3 = "lName"
        private val COL4 = "pNumber"
    }

}