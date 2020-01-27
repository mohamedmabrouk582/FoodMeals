package com.example.foodmeals.viewModels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.JuiceHomeCallBack
import com.example.foodmeals.data.api.JuiceApi
import com.example.foodmeals.data.db.MealDao
import com.example.foodmeals.data.models.FiltersType
import com.example.foodmeals.data.models.FoodType
import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.data.models.MealsResponse
import com.example.foodmeals.ui.activities.MealsActivity
import com.example.foodmeals.utils.network.RequestCoroutines
import com.example.foodmeals.utils.network.RequestListener
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DrinkHomeViewModel<v:JuiceHomeCallBack>(application: Application,val api:JuiceApi,val dao:MealDao) : BaseViewModel<v>(application) ,  RequestCoroutines{
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
                    data.value?.meals?.get(0)?.apply {
                     this.type=FoodType.Drinks
                     this.mealType=FoodType.DrinksRandom

                        GlobalScope.launch (Dispatchers.IO){ dao.insertOneMeal(this@apply)  }
                     view.loadJuice(this)
                    }
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
                dao.getMeal(FoodType.DrinksRandom).observe(view.getJuiceFragment(), Observer {

                    it?.apply {
                        forEach {juice->
                            view.loadJuice(juice)
                        }
                    }
                })
            }

        })
    }

    fun reqAlcoholic(){
        alcoholicLoader.set(true)
        alcoholicError.set(null)
        api.filter(alcoholic = FoodType.Alcoholic.name).handelEx(getApplication(),object : RequestListener<MealsResponse>{
            override fun onResponse(data: MutableLiveData<MealsResponse>) {
                if (data.value!=null || data.value?.meals !=null){
                  alcoholicLoader.set(false)
                  view.loadAlcoholic(filterMealsType(data.value?.meals!!,FoodType.Alcoholic))
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
                dao.getMeal(FoodType.Alcoholic).observe(view.getJuiceFragment(), Observer {
                    alcoholicLoader.set(false)
                    if (it.isNullOrEmpty())
                        alcoholicError.set(msg)
                    else view.loadAlcoholic(it as ArrayList<Meal>)
                })
//                alcoholicError.set(msg)
            }

        })
    }

    fun filterMealsType(data:ArrayList<Meal>,type:FoodType) : ArrayList<Meal> =
        data.apply {
            forEach {
                it.type=FoodType.Drinks
                it.mealType=type
            }
            GlobalScope.launch(Dispatchers.IO) { dao.insertMeal(this@apply) }
        }


    fun reqNonAlcoholic(){
        nonAlcoholicLoader.set(true)
        nonAlcoholicError.set(null)
        api.filter(alcoholic = FoodType.Non_Alcoholic.name).handelEx(getApplication(),object : RequestListener<MealsResponse>{
            override fun onResponse(data: MutableLiveData<MealsResponse>) {
                if (data.value!=null || data.value?.meals !=null){
                    nonAlcoholicLoader.set(false)
                    view.loadNonAlcoholic(filterMealsType(data.value?.meals!!,FoodType.Non_Alcoholic))
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
                dao.getMeal(FoodType.Non_Alcoholic).observe(view.getJuiceFragment(), Observer {
                    nonAlcoholicLoader.set(false)
                    if (it.isNullOrEmpty())
                        nonAlcoholicError.set(msg)
                    else view.loadNonAlcoholic(it as ArrayList<Meal>)
                })
                //nonAlcoholicError.set(msg)
            }

        })
    }

    fun search() {
        MealsActivity.start(getApplication(),0,"", FiltersType.Search,FoodType.Drinks)
    }

}