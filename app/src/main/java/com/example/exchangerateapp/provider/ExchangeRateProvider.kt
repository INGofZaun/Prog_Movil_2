package com.example.exchangerateapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.exchangerateapp.data.ExchangeRateDatabase

class ExchangeRateProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.exchangerateapp.provider"
        const val TABLE_NAME = "exchange_rates"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }

    private lateinit var database: ExchangeRateDatabase

    override fun onCreate(): Boolean {
        context?.let {
            database = ExchangeRateDatabase.getDatabase(it)
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME" + if (!selection.isNullOrEmpty()) " WHERE $selection" else ""

        val cursor = database.openHelper.readableDatabase.query(
            SimpleSQLiteQuery(query, selectionArgs ?: emptyArray())
        )

        // Notificar cambios
        context?.contentResolver?.notifyChange(uri, null)

        return cursor
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.dir/vnd.$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null // Solo lectura
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0 // No permitimos eliminar datos desde el ContentProvider
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0 // No permitimos modificar datos desde el ContentProvider
    }
}
