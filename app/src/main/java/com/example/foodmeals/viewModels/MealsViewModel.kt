package com.example.foodmeals.viewModels

import android.app.Application
import androidx.appcompat.widget.SearchView
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.MealsCallBack
import com.example.foodmeals.data.api.BaseApi
import com.example.foodmeals.data.api.JuiceApi
import com.example.foodmeals.data.models.FiltersType
import com.example.foodmeals.data.models.MealsResponse
import com.example.foodmeals.utils.network.RequestCoroutines
import com.example.foodmeals.utils.network.RequestListener
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack
import kotlinx.coroutines.Deferred


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class MealsViewModel<v : MealsCallBack> (application: Application,val api:BaseApi,val juiceApi: JuiceApi): BaseViewModel<v>(application) , RequestCoroutines{
    val loader:ObservableBoolean = ObservableBoolean()
    val error:ObservableField<String> = ObservableField()
    lateinit var filterType : FiltersType
     var isDrink:Boolean=false
     var query:String=""
    val callBack:RetryCallBack = object : RetryCallBack{
        override fun onRetry() {
            reqMeals(query,filterType)
        }

    }

    val listener:SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.apply {
                if (isDrink)
                    reqDrink(this,filterType)
                else
                    reqMeals(this,filterType)
            }
         return true
        }

    }


    fun reqMeals(query:String, type:FiltersType){
        filterType=type
        this.query=query
        loader.set(true)
        error.set(null)
       when(type){
            FiltersType.Category -> api.getFilterByCatgory(query)
            FiltersType.Ingredient -> api.getFilterByIngredient(query)
            FiltersType.Area -> api.getFilterByArea(query)
            FiltersType.Search -> api.searchMeals(query)
        }.handelEx(getApplication(),requestListener)
    }

    fun reqDrink(query:String,type:FiltersType){
        filterType=type
        isDrink=true
        this.query=query
        loader.set(true)
        error.set(null)
        val response:Deferred<MealsResponse> = if (type== FiltersType.Ingredient)   juiceApi.filter(ingredient = query) else juiceApi.search(query)
        response.handelEx(getApplication(),requestListener)
    }


    val requestListener: RequestListener<MealsResponse> = object : RequestListener<MealsResponse>{
        override fun onResponse(data: MutableLiveData<MealsResponse>) {
            if (data.value?.meals!=null){
                loader.set(false)
                view.loadMeals(data = data.value?.meals!!)
            } else onError(getApplication<Application>().getString(R.string.no_data_found))
        }

        override fun onError(msg: String) {
            loader.set(false)
            error.set(msg)
            view.error(msg)
        }

        override fun onSessionExpired(msg: String) {
            loader.set(false)
            error.set(msg)
            view.error(msg)
        }

        override fun onNetWorkError(msg: String) {
            loader.set(false)
            error.set(msg)
            view.error(msg)
        }

    }




}