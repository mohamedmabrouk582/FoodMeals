package com.example.foodmeals.di.modules

import com.example.foodmeals.data.api.BaseApi
import com.example.foodmeals.data.api.JuiceApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiModule (private val juiceUrl:String, private val foodUrl:String){
    val apiModule = module(override = true){

        single {
            get<Retrofit>(named("food")).create(BaseApi::class.java)
        }

        single{
            get<Retrofit>(named("juice")).create(JuiceApi::class.java)
        }

        factory (named("juice")){
            Retrofit.Builder()
                .baseUrl(juiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(get()).build()
        }

        factory (named("food")){
            Retrofit.Builder()
                .baseUrl(foodUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(get()).build()
        }

        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        }
    }
}