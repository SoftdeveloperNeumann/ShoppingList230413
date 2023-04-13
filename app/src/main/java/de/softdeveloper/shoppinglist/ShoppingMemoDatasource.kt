package de.softdeveloper.shoppinglist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class ShoppingMemoDatasource(context:Context) {

    private val TAG = "ShoppingMemoDataSource"

    private var db: SQLiteDatabase? =null
    private val helper: ShoppingMemoDbHelper

    init {
        helper = ShoppingMemoDbHelper(context)
        Log.d(TAG, "DB-Init: Datasource hat den Helper angelegt")
    }

    fun open(){
        db = helper.writableDatabase
    }

    fun close(){
        helper.close()
    }
}