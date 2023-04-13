package de.softdeveloper.shoppinglist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ShoppingMemoDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {

    companion object{
        private const val DB_NAME = "shopping_list.db"
        private const val DB_VERSION = 1
        private const val TAG = "ShoppingMemoDbHelper"

        // Deklaration der Tabelleneigenschaften
        const val TABEL_SHOPPING_LIST = "shopping_list"
        const val COLUMN_ID = "_id"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_PRODUCT = "product"
    }

    private val SQL_CREATE_TABLE = """
        CREATE TABLE $TABEL_SHOPPING_LIST
        ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_QUANTITY INTEGER NOT NULL,
        $COLUMN_PRODUCT TEXT NOT NULL);
    """

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "onCreateDbHelper: Die Tabelle $TABEL_SHOPPING_LIST wird erstellt")
        try {
            db?.execSQL(SQL_CREATE_TABLE)
        }catch (e:java.lang.Exception){
            Log.e(TAG, "onCreateDbHelper: Fehler beim erstellen der Tabelle", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}