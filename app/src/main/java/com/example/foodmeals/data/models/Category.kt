package com.example.foodmeals.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/
@Entity
data class Category(@PrimaryKey val idCategory:Long, val strCategory:String?, val strCategoryThumb:String?, val strCategoryDescription:String?) : Serializable