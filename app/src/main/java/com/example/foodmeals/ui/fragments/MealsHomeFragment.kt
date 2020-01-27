package com.example.foodmeals.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.MealsHomeCallBack
import com.example.foodmeals.data.models.*
import com.example.foodmeals.databinding.MealsHomeLayoutBinding
import com.example.foodmeals.ui.activities.MealDetailsActivity
import com.example.foodmeals.ui.activities.MealsActivity
import com.example.foodmeals.ui.adapters.CategoryAdapter
import com.example.foodmeals.ui.adapters.IngredientAdapter
import com.example.foodmeals.ui.base.BaseFragment
import com.example.foodmeals.viewModels.MealsHomeViewModel
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.example.foodmeals.viewModels.base.BaseViewModelFactory
import com.mabrouk.slideroval.DefaultSliderView
import com.mabrouk.slideroval.ScrollTimeType
import com.mabrouk.slideroval.SliderAnimations
import kotlinx.android.synthetic.main.meals_home_layout.*
import org.koin.android.ext.android.inject


/*
* Created By mabrouk on 11/09/19
* Cook Meals
*/

class MealsHomeFragment : BaseFragment() , MealsHomeCallBack {
    override fun getFragment(): MealsHomeFragment = this

    lateinit var layoutBinding: MealsHomeLayoutBinding
    val factory:BaseViewModelFactory by inject<BaseViewModelFactory>()
    lateinit var viewModelMeals: MealsHomeViewModel<MealsHomeCallBack>
    val mealsAdapter:IngredientAdapter by lazy {
        IngredientAdapter(object : IngredientAdapter.IngredientListener{
            override fun onIngredientClick(ingredient: Ingredient) {
                MealsActivity.start(context!!,ingredient.idIngredient,ingredient.content,FiltersType.Ingredient,FoodType.Meals)
            }
        })
    }
    val categoryAdapter:CategoryAdapter by lazy {
        CategoryAdapter(object : CategoryAdapter.CategoryListener{
            override fun onCategoryClick(category: Category) {
                MealsActivity.start(
                    context!!,
                    category.idCategory,
                    category.strCategory!!,
                    FiltersType.Category,FoodType.Meals
                )
            }

        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBinding=DataBindingUtil.inflate(inflater,R.layout.meals_home_layout,container,false)

        return layoutBinding.root
    }

    override fun initView() {
        sliderLayout.setScrollTimeInSec(2,ScrollTimeType.SECOND)
        sliderLayout.setPagerIndicatorVisibility(false)
        sliderLayout.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        non_alcoholic_rcv.layoutManager= LinearLayoutManager(context!!,LinearLayoutManager.HORIZONTAL,false)
        alcoholic_rcv.layoutManager= LinearLayoutManager(context!!,LinearLayoutManager.HORIZONTAL,false)
        alcoholic_rcv.adapter= mealsAdapter
        non_alcoholic_rcv.adapter=categoryAdapter
        viewModelMeals= getViewmodel(activity!!,factory)
        viewModelMeals.attachView(this)
        viewModelMeals.reqRandomMeal(20)
        viewModelMeals.reqCategory()
        viewModelMeals.reqIngredients()
        layoutBinding.homeVm=viewModelMeals
    }

    override fun addRandomMel(meal: Meal) {
      val  sliderView = DefaultSliderView(context!!)
        sliderView.imageUrl = meal.strMealThumb
        sliderView.setOnSliderClickListener {
            MealDetailsActivity.start(
                context!!,
                meal.idMeal,FoodType.Meals
            )
        }
        sliderLayout.addSliderView(sliderView)
    }

    override fun loadCategories(data: ArrayList<Category>) {
       categoryAdapter.setData(data)
    }

    override fun loadIngredients(data: ArrayList<Ingredient>) {
      mealsAdapter.setData(data)
    }

    override fun error(msg: String) {
      Toast.makeText(context!!,msg,Toast.LENGTH_SHORT).show()
    }

    inline fun <reified T : BaseViewModel<MealsHomeCallBack>> getViewmodel(activity: FragmentActivity, factory:BaseViewModelFactory):T{
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }

}