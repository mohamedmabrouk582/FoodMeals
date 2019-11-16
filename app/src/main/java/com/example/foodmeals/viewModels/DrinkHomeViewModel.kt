package com.example.foodmeals.viewModels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.JuiceHomeCallBack
import com.example.foodmeals.data.api.JuiceApi
import com.example.foodmeals.data.models.DrinkType
import com.example.foodmeals.data.models.FiltersType
import com.example.foodmeals.data.models.FoodType
import com.example.foodmeals.data.models.MealsResponse
import com.example.foodmeals.ui.activities.MealsActivity
import com.example.foodmeals.utils.network.RequestCoroutines
import com.example.foodmeals.utils.network.RequestListener
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack

class DrinkHomeViewModel<v:JuiceHomeCallBack>(application: Application,val api:JuiceApi) : BaseViewModel<v>(application) ,  RequestCoroutines{
    val alcoholicLoader : ObservableBoolean = ObservableBoolean()
    val nonAlcoholicLoader: ObservableBoolean = ObservableBoolean()
    val alcoholicError: ObservableField<String> = ObservableField()
    val nonAlcoholicError : ObservableField<String> = ObservableField()
    val alcoholicCallBack:RetryCallBack= object : RetryCallBack{
        override fun onRetry() {
            reqAlcoholic()
        }

    }
    val NonAlcoholicCallBack:RetryCallBack = object :RetryCallBack{
        override fun onRetry() {
            reqNonAlcoholic()
        }

    }


    fun reqRandom(limit:Int){
        api.getRandomJuice().handelEx(getApplication(),object : RequestListener<MealsResponse>{
            override fun onResponse(data: MutableLiveData<MealsResponse>) {
                if (data.value!=null || data.value?.meals !=null) {
                  view.loadJuice(data.value?.meals?.get(0)!!)
                    if (limit>0){
                        reqRandom(limit-1)
                    }
                } else onError(getApplication<Application>().getString(R.string.no_data_found))

             }

            override fun onError(msg: String) {
                   reqRandom(limit)
            }

            override fun onSessionExpired(msg: String) {
                    reqRandom(limit)
            }

            override fun onNetWorkError(msg: String) {
               view.error(msg)
            }

        })
    }

    fun reqAlcoholic(){
        alcoholicLoader.set(true)
        alcoholicError.set(null)
        api.filter(alcoholic = DrinkType.Alcoholic.name).handelEx(getApplication(),object : RequestListener<MealsResponse>{
            override fun onResponse(data: MutableLiveData<MealsResponse>) {
                if (data.value!=null || data.value?.meals !=null){
                  alcoholicLoader.set(false)
                  view.loadAlcoholic(data.value?.meals!!)
                }else onError(getApplication<Application>().getString(R.string.no_data_found))
            }

            override fun onError(msg: String) {
             alcoholicLoader.set(false)
             alcoholicError.set(msg)
            }

            override fun onSessionExpired(msg: String) {
                alcoholicLoader.set(false)
                alcoholicError.set(msg)
            }

            override fun onNetWorkError(msg: String) {
                alcoholicLoader.set(false)
                alcoholicError.set(msg)
            }

        })
    }

    fun reqNonAlcoholic(){
        nonAlcoholicLoader.set(true)
        nonAlcoholicError.set(null)
        api.filter(alcoholic = DrinkType.Non_Alcoholic.name).handelEx(getApplication(),object : RequestListener<MealsResponse>{
            override fun onResponse(data: MutableLiveData<MealsResponse>) {
                if (data.value!=null || data.value?.meals !=null){
                    nonAlcoholicLoader.set(false)
                    view.loadNonAlcoholic(data.value?.meals!!)
                }else onError(getApplication<Application>().getString(R.string.no_data_found))
            }

            override fun onError(msg: String) {
                nonAlcoholicLoader.set(false)
                nonAlcoholicError.set(msg)
            }

            override fun onSessionExpired(msg: String) {
                nonAlcoholicLoader.set(false)
                nonAlcoholicError.set(msg)
            }

            override fun onNetWorkError(msg: String) {
                nonAlcoholicLoader.set(false)
                nonAlcoholicError.set(msg)
            }

        })
    }

    fun search() {
        MealsActivity.start(getApplication(),"", FiltersType.Search,FoodType.Drinks)
    }

}