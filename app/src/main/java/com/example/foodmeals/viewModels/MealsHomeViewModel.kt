package com.example.foodmeals.viewModels

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.MealsHomeCallBack
import com.example.foodmeals.data.api.BaseApi
import com.example.foodmeals.data.db.MealDao
import com.example.foodmeals.data.models.*
import com.example.foodmeals.ui.activities.MealsActivity
import com.example.foodmeals.ui.fragments.CountryFragment
import com.example.foodmeals.utils.network.RequestCoroutines
import com.example.foodmeals.utils.network.RequestListener
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class MealsHomeViewModel<v :MealsHomeCallBack>(application: Application, val api: BaseApi,val dao: MealDao) : BaseViewModel<v>(application),RequestCoroutines {
    val categoryLoader : ObservableBoolean = ObservableBoolean()
    val mealsLoader: ObservableBoolean = ObservableBoolean()
    val categoryError: ObservableField<String> = ObservableField()
    val mealsError : ObservableField<String> = ObservableField()

    val catergoryCallBack:RetryCallBack = object : RetryCallBack{
        override fun onRetry() {
          reqCategory()
        }
    }

    val mealsCallBack : RetryCallBack = object : RetryCallBack{
        override fun onRetry() {
            reqIngredients()
        }

    }

    fun reqRandomMeal(limit:Int){
      api.getRandomMeals().handelEx(getApplication(), object : RequestListener<MealsResponse>{
          override fun onResponse(data: MutableLiveData<MealsResponse>) {
              if (data.value !=null && data.value?.meals !=null){
                  data.value?.meals?.get(0)?.apply {
                      this.type=FoodType.Meals
                      this.mealType=FoodType.MealsRandom
                      GlobalScope.launch (Dispatchers.IO){ dao.insertOneMeal(this@apply)  }
                      view.addRandomMel(this)
                  }

                  if (limit>0){
                      reqRandomMeal(limit-1)
                  }
              } else onError(getApplication<Application>().getString(R.string.no_data_found))
          }

          override fun onError(msg: String) {
              reqRandomMeal(limit)
          }

          override fun onSessionExpired(msg: String) {
              reqRandomMeal(limit)
          }

          override fun onNetWorkError(msg: String) {
            view.error(msg)
             dao.getMeal(FoodType.MealsRandom).observe(view.getFragment(), Observer {
                 it.apply {
                     forEach { meal ->
                         view.addRandomMel(meal)
                     }
                 }
             })
          }

      })
    }

    fun reqCategory(){
        categoryLoader.set(true)
        categoryError.set(null)
        api.getCategories().handelEx(getApplication(),object : RequestListener<CategoryResponse>{
            override fun onResponse(data: MutableLiveData<CategoryResponse>) {
              if (data.value?.categories!=null){
                  GlobalScope.launch(Dispatchers.IO) {
                      dao.insertCategory(data.value?.categories!!)
                  }
                  categoryLoader.set(false)
                  view.loadCategories(data.value?.categories!!)
              } else onError(getApplication<Application>().getString(R.string.no_data_found))
            }

            override fun onError(msg: String) {
              categoryLoader.set(false)
                categoryError.set(msg)
            }

            override fun onSessionExpired(msg: String) {
                categoryLoader.set(false)
                categoryError.set(msg)
            }

            override fun onNetWorkError(msg: String) {
                dao.getCategory().observe(view.getFragment(), Observer {
                    categoryLoader.set(false)
                    if (it.isNullOrEmpty())
                        categoryError.set(msg)
                    else view.loadCategories(it as ArrayList<Category>)
                    Log.d("MealsByCategory","$it")
                })

                //categoryError.set(msg)
            }

        })
    }

    fun search() {
      MealsActivity.start(getApplication(),0,"",FiltersType.Search,FoodType.Meals)
    }
    fun reqIngredients(){
        mealsError.set(null)
        mealsLoader.set(true)
        api.getIngredients().handelEx(getApplication(),object : RequestListener<IngredientResponse>{
            override fun onResponse(data: MutableLiveData<IngredientResponse>) {
                if (data.value?.meals!=null){
                   mealsLoader.set(false)
                    GlobalScope.launch (Dispatchers.IO){
                        dao.insertIngredient(data.value?.meals!!)
                    }
                    view.loadIngredients(data.value?.meals!!)
                } else onError(getApplication<Application>().getString(R.string.no_data_found))
            }

            override fun onError(msg: String) {
               mealsLoader.set(false)
                mealsError.set(msg)
            }

            override fun onSessionExpired(msg: String) {
                mealsLoader.set(false)
                mealsError.set(msg)
            }

            override fun onNetWorkError(msg: String) {
                dao.getIngredient().observe(view.getFragment(), Observer {
                    mealsLoader.set(false)
                    if (it.isNullOrEmpty())
                        mealsError.set(msg)
                    else view.loadIngredients(it as ArrayList<Ingredient>)
                    Log.d("MealsByIngredient","$it")

                })

                //mealsError.set(msg)
            }

        })
    }

    fun countryClick(){
        CountryFragment.getFragment().show(view.getFragment().childFragmentManager,"CountryFragment")
    }
}