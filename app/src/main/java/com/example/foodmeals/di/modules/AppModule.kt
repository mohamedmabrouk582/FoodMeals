package com.example.foodmeals.di.modules

import com.example.foodmeals.utils.SharedManager
import com.example.foodmeals.viewModels.base.BaseViewModelFactory
import org.koin.dsl.module

class AppModule {
    val appModule= module (override = true){
        single {
            BaseViewModelFactory(get(),get(),get(),get())
        }

        single {
            SharedManager(get(),"meals")
        }
    }
}