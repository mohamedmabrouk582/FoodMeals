package com.example.foodmeals.viewModels

import android.app.Application
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.MealsCallBack
import com.example.foodmeals.data.api.BaseApi
import com.example.foodmeals.data.api.JuiceApi
import com.example.foodmeals.data.db.MealDao
import com.example.foodmeals.data.models.FiltersType
import com.example.foodmeals.data.models.FoodType
import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.data.models.MealsResponse
import com.example.foodmeals.utils.network.RequestCoroutines
import com.example.foodmeals.utils.network.RequestListener
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack
import kotlinx.coroutines.*


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class MealsViewModel<v : MealsCallBack> (application: Application,val api:BaseApi,val juiceApi: JuiceApi,val dao:MealDao): BaseViewModel<v>(application) , RequestCoroutines{
    val loader:ObservableBoolean = ObservableBoolean()
    val error:ObservableField<String> = ObservableField()
    lateinit var filterType : FiltersType
     var isDrink:Boolean=false
     var query:String=""
     var id:Long=0
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
        dao.getMealsByIngredients().observe(view.getMealsActivity(), Observer {
            it.forEach {
                Log.d("hello", "${it.meals}")
            }
        })
       when(type){
            FiltersType.Category -> api.getFilterByCatgory(query)
            FiltersType.Ingredient -> api.getFilterByIngredient(query)
            FiltersType.Area -> api.getFilterByArea(query)
            FiltersType.Search -> api.searchMeals(query)
        }.handelEx(getApplication(),requestListener(FoodType.Meals))
    }

    fun reqDrink(query:String,type:FiltersType){
        filterType=type
        isDrink=true
        this.query=query
        loader.set(true)
        error.set(null)
        val response:Deferred<MealsResponse> = if (type== FiltersType.Ingredient)   juiceApi.filter(ingredient = query) else juiceApi.search(query)
        response.handelEx(getApplication(),requestListener(FoodType.Drinks))
    }

    private fun requestListener(type: FoodType) : RequestListener<MealsResponse> = object : RequestListener<MealsResponse>{
        override fun onResponse(data: MutableLiveData<MealsResponse>) {
            if (data.value?.meals!=null){
                loader.set(false)
                view.loadMeals(data = filterMealsType(data.value?.meals!!,type))
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

        override fun onNetWorkError(msg: String) = runBlocking{
            loader.set(false)
            when(filterType){
                FiltersType.Category -> dao.getMealsByCategory(query).observe(view.getMealsActivity(), Observer { loadData(it ,msg) })
                FiltersType.Ingredient -> dao.getMealsByIngredients().observe(view.getMealsActivity(), Observer { loadData(it[0].meals ,msg)  })
                FiltersType.Search ->  (if (query.isEmpty()) dao.getMeal(type)else dao.getSearchMeal(type, query)).observe(view.getMealsActivity(), Observer { loadData(it,msg) })
                FiltersType.Area ->  dao.getMealsByArea(type,query).observe(view.getMealsActivity(), Observer { loadData(it,msg) })
            }
        }

    }

    fun loadData(data: List<Meal>, msg:String){
        if (data.isNullOrEmpty()){ error.set(msg);view.error(msg)}
        else view.loadMeals(data as ArrayList<Meal>)
    }


    fun filterMealsType(data:ArrayList<Meal>,type: FoodType) : ArrayList<Meal> =
        data.apply {
            GlobalScope.launch (Dispatchers.IO){
                when (filterType) {
                    FiltersType.Category -> {
                        forEach {
                            it.strCategory=query
                            it.type= type
                        }
                    }
                    FiltersType.Area -> {
                        forEach {
                            it.area=query
                            it.type= type
                        }
                    }
                    else -> {
                        forEach {
                            it.type= type
                        }
                    }
                }
                    dao.insertMeal(this@apply)

            }
        }

}