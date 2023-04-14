package de.softdeveloper.shoppinglist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class ShoppingMemoDatasource(context:Context) {

    private val TAG = "ShoppingMemoDataSource"

    private var db: SQLiteDatabase? =null
    private val helper: ShoppingMemoDbHelper

    private val columns= arrayOf(
        ShoppingMemoDbHelper.COLUMN_ID,
        ShoppingMemoDbHelper.COLUMN_QUANTITY,
        ShoppingMemoDbHelper.COLUMN_PRODUCT,
    )

    val allShoppingMemos: List<ShoppingMemo>
    get() {
        val shoppingMemoList: MutableList<ShoppingMemo> = ArrayList()
        val cursor = db?.query(
            ShoppingMemoDbHelper.TABEL_SHOPPING_LIST,
            columns,
            null,null,null,null,null
        )
        cursor?.moveToFirst()
        var memo:ShoppingMemo
        while (!cursor?.isAfterLast!!){
            memo = cursorToShoppingMemo(cursor)
            shoppingMemoList.add(memo)
            cursor.moveToNext()
        }
        cursor.close()
        return shoppingMemoList
    }

    init {
        helper = ShoppingMemoDbHelper(context)
        Log.d(TAG, "DB-Init: Datasource hat den Helper angelegt")
    }

    private fun cursorToShoppingMemo(cursor: Cursor): ShoppingMemo {
        val idIndex = cursor.getColumnIndex(ShoppingMemoDbHelper.COLUMN_ID)
        val quantityIndex = cursor.getColumnIndex(ShoppingMemoDbHelper.COLUMN_QUANTITY)
        val productIndex = cursor.getColumnIndex(ShoppingMemoDbHelper.COLUMN_PRODUCT)

        val id = cursor.getLong(idIndex)
        val quantity = cursor.getInt(quantityIndex)
        val product = cursor.getString(productIndex)

        return ShoppingMemo(quantity, product, id)
    }

    fun createShoppingMemo(quantity:Int,product:String):ShoppingMemo{
        val values = ContentValues().apply {
            put(ShoppingMemoDbHelper.COLUMN_QUANTITY, quantity)
            put(ShoppingMemoDbHelper.COLUMN_PRODUCT, product)
        }

        val insertId = db?.insert(ShoppingMemoDbHelper.TABEL_SHOPPING_LIST,null,values)?: -1
        val cursor = db?.query(
            ShoppingMemoDbHelper.TABEL_SHOPPING_LIST,
            columns,
            "${ShoppingMemoDbHelper.COLUMN_ID} = $insertId",
            null,null,null,null
        )
        cursor?.moveToFirst()
        val memo = cursorToShoppingMemo(cursor!!)
        cursor.close()
        return memo
    }



    fun open(){
        db = helper.writableDatabase
    }

    fun close(){
        helper.close()
    }
}