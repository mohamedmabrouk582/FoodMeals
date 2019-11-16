package com.example.foodmeals.di.modules

import android.content.Context
import androidx.room.Room
import com.example.foodmeals.data.db.MealDb
import org.koin.dsl.module

class RoomModule(val db:String) {
    val roomModule = module (override = true){
        single {
            Room.databaseBuilder(get(),MealDb::class.java,db).fallbackToDestructiveMigration()
                .build().getMealDao()
        }
    }
}