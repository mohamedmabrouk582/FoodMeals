package com.example.foodmeals.viewModels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.MealsHomeCallBack
import com.example.foodmeals.data.api.BaseApi
import com.example.foodmeals.data.models.*
import com.example.foodmeals.ui.activities.MealsActivity
import com.example.foodmeals.ui.fragments.CountryFragment
import com.example.foodmeals.utils.network.RequestCoroutines
import com.example.foodmeals.utils.network.RequestListener
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class MealsHomeViewModel<v :MealsHomeCallBack>(application: Application, val api: BaseApi) : BaseViewModel<v>(application),RequestCoroutines {
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
            reqLatestMeals()
        }

    }

    fun reqRandomMeal(limit:Int){
      api.getRandomMeals().handelEx(getApplication(), object : RequestListener<MealsResponse>{
          override fun onResponse(data: MutableLiveData<MealsResponse>) {
              if (data.value !=null && data.value?.meals !=null){
                  view.addRandomMel(data.value?.meals?.get(0)!!)
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
          }

      })
    }

    fun reqCategory(){
        categoryLoader.set(true)
        categoryError.set(null)
        api.getCategories().handelEx(getApplication(),object : RequestListener<CategoryResponse>{
            override fun onResponse(data: MutableLiveData<CategoryResponse>) {
              if (data.value?.categories!=null){
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
                categoryLoader.set(false)
                categoryError.set(msg)
            }

        })
    }

    fun search() {
      MealsActivity.start(getApplication(),"",FiltersType.Search,FoodType.Meals)
    }
    fun reqLatestMeals(){
        mealsError.set(null)
        mealsLoader.set(true)
        api.getIngredients().handelEx(getApplication(),object : RequestListener<IngredientResponse>{
            override fun onResponse(data: MutableLiveData<IngredientResponse>) {
                if (data.value?.meals!=null){
                   mealsLoader.set(false)
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
                mealsLoader.set(false)
                mealsError.set(msg)
            }

        })
    }

    fun countryClick(){
        CountryFragment.getFragment().show(view.getFragment().childFragmentManager,"CountryFragment")
    }
}