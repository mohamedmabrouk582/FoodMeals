package com.example.foodmeals.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.foodmeals.di.modules.ApiModule
import com.example.foodmeals.di.modules.AppModule
import com.example.foodmeals.di.modules.RoomModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        startKoin {
            androidContext(this@MyApp)
            modules(AppModule().appModule,ApiModule("https://www.thecocktaildb.com/api/json/v1/1/","https://www.themealdb.com/api/json/v1/1/").apiModule
            ,RoomModule("meals").roomModule)
        }
    }


}
