package com.example.randomstringgenerator.data.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.example.randomstringgenerator.data.model.RandomText
import com.example.randomstringgenerator.data.model.StringModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RandomStringRepository(context: Context) {
    private var _randomStringList = mutableListOf<RandomText>()

    private val contentResolver: ContentResolver = context.contentResolver
    private val gson: Gson = Gson()

    fun getAllRandomGeneratedStrings(): List<RandomText> {
        return _randomStringList
    }

    fun addRandomString(length: Int) {
        val uri = Uri.parse("content://com.iav.contestdataprovider/text")
        val cursor: Cursor? =
            contentResolver.query(uri, null, null, arrayOf(length.toString()), null)

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val jsonString = cursor.getString(0)
                val randomStringData: StringModel =
                    gson.fromJson(jsonString, object : TypeToken<StringModel>() {}.type)
                _randomStringList.add(randomStringData.randomText)
            }
            cursor.close()
        }
    }

    fun deleteRandomString(randomText: RandomText) {
        _randomStringList.removeIf {
            it == randomText
        }
    }

    fun deleteAllRandomString() {
        _randomStringList.clear()
    }
}