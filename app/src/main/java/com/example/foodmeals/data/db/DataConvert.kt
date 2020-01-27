package com.example.foodmeals.data.db

import androidx.room.TypeConverter
import com.example.foodmeals.data.models.FoodType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConvert {

    @TypeConverter
    fun convertEnum(type:FoodType?) : String? = type?.let { Gson().toJson(type) }

    @TypeConverter
    fun convertToEnum(data:String?) : FoodType? {
        val type= object : TypeToken<FoodType>(){}.type
        return  data?.let { Gson().fromJson(data,type) }
    }
}