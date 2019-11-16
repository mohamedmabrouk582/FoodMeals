package com.example.foodmeals.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/*
* Created By mabrouk on 01/09/19
* FlickrImage App
*/

class SharedManager(val context: Context, private val sharedName: String) {
    val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            sharedName,
            Context.MODE_PRIVATE
        )
    }
    val gson: Gson by lazy { Gson() }

    fun saveBoolean(key: String, value: Boolean) {
        GlobalScope.launch(Dispatchers.IO) {
            sharedPreferences.edit().putBoolean(key, value).commit()
        }
    }

    fun getBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)

    fun saveString(key: String, value: String) {
        GlobalScope.launch(Dispatchers.IO) {
            sharedPreferences.edit().putString(key, value).commit()
        }
    }

    fun getString(key: String): String? = sharedPreferences.getString(key, null)

    fun saveFloat(key: String, value: Float) {
        GlobalScope.launch(Dispatchers.IO) {
            sharedPreferences.edit().putFloat(key, value).commit()
        }
    }

    fun getFloat(key: String): Float = sharedPreferences.getFloat(key, 0f)

    fun saveInt(key: String, value: Int) {
        GlobalScope.launch(Dispatchers.IO) { sharedPreferences.edit().putInt(key, value).commit() }
    }

    fun getInt(key: String): Int = sharedPreferences.getInt(key, 0)

    fun saveLong(key: String, value: Long) {
        GlobalScope.launch(Dispatchers.IO) { sharedPreferences.edit().putLong(key, value).commit() }
    }

    fun getLong(key: String): Long = sharedPreferences.getLong(key, 0L)

    fun <t> saveData(key: String, value: t) {
        GlobalScope.launch(Dispatchers.IO) { saveString(key, gson.toJson(value)) }
    }

    fun <t> getData(key: String): t {
        val type = object : TypeToken<t>() {}.type
        return gson.fromJson(getString(key), type)
    }
}
