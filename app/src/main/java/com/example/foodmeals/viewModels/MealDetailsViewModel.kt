package com.example.foodmeals.viewModels

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.MealDetailsCallBack
import com.example.foodmeals.data.api.BaseApi
import com.example.foodmeals.data.api.JuiceApi
import com.example.foodmeals.data.models.MealsResponse
import com.example.foodmeals.utils.network.RequestCoroutines
import com.example.foodmeals.utils.network.RequestListener
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack


/*
* Created By mabrouk on 13/09/190
* Cook Meals
*/

class MealDetailsViewModel<v : MealDetailsCallBack>(application: Application, val api: BaseApi, val juiceApi:JuiceApi) : BaseViewModel<v>(application), RequestCoroutines {
    val loader:ObservableBoolean      = ObservableBoolean()
    val error:ObservableField<String> = ObservableField()
    var id:Long=0
    val callBack:RetryCallBack= object : RetryCallBack{
        override fun onRetry() {
            reqMeal(id)
        }

    }
    fun reqMeal(id:Long){
        this.id=id
        loader.set(true)
        error.set(null)
        api.getMealDetails(id).handelEx(getApplication(),listener)
    }

    fun reqJuice(id:Long){
        this.id=id
        loader.set(true)
        error.set(null)
        juiceApi.getJuiceDetails(id).handelEx(getApplication(),listener)
    }

    val listener : RequestListener<MealsResponse> = object : RequestListener<MealsResponse>{
        override fun onResponse(data: MutableLiveData<MealsResponse>) {
            if (data.value?.meals!=null){
                loader.set(false)
                view.loadMeal(data.value?.meals?.get(0)!!)
            } else onError(getApplication<Application>().getString(R.string.no_data_found))
        }

        override fun onError(msg: String) {
            loader.set(false)
            error.set(msg)
            //reqMeal(id)
        }

        override fun onSessionExpired(msg: String) {
            loader.set(false)
            error.set(msg)
            //reqMeal(id)

        }

        override fun onNetWorkError(msg: String) {
            loader.set(false)
            error.set(msg)
            //reqMeal(id)
        }

    }

    fun back(){
        view.getMealActivity().finish()
    }

    fun youtube(url:String?){
            url?.apply {
                split('=')
                val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${split('=')[1]}"))
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$id"))
                try {
                    appIntent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                    getApplication<Application>().startActivity(appIntent)
                } catch (ex: Exception) {
                    webIntent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                    getApplication<Application>().startActivity(webIntent)
                }
            }

    }

    fun link(link:String?){
        link?.apply {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(this))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            getApplication<Application>().startActivity(intent)
        }
    }

}