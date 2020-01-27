package com.example.foodmeals.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.JuiceHomeCallBack
import com.example.foodmeals.data.api.JuiceApi
import com.example.foodmeals.data.models.FoodType
import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.databinding.JuiceHomeLayoutBinding
import com.example.foodmeals.ui.activities.MealDetailsActivity
import com.example.foodmeals.ui.adapters.DrinkAdapter
import com.example.foodmeals.ui.base.BaseFragment
import com.example.foodmeals.utils.network.RequestCoroutines
import com.example.foodmeals.viewModels.DrinkHomeViewModel
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.example.foodmeals.viewModels.base.BaseViewModelFactory
import com.mabrouk.slideroval.DefaultSliderView
import com.mabrouk.slideroval.ScrollTimeType
import com.mabrouk.slideroval.SliderAnimations
import kotlinx.android.synthetic.main.juice_home_layout.*
import org.koin.android.ext.android.inject

class JuiceHomeFragment : BaseFragment() , JuiceHomeCallBack ,RequestCoroutines, DrinkAdapter.DrinkListener {
    val api:JuiceApi by inject<JuiceApi>()
    val factory: BaseViewModelFactory by inject<BaseViewModelFactory>()
    lateinit var layoutBinding: JuiceHomeLayoutBinding
    lateinit var viewModel:DrinkHomeViewModel<JuiceHomeCallBack>
    val alcoholicAdapter: DrinkAdapter by lazy { DrinkAdapter(this) }
    val nonAlcoholicAdapter:DrinkAdapter by lazy { DrinkAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBinding=DataBindingUtil.inflate(inflater, R.layout.juice_home_layout,container,false)

        return layoutBinding.root
    }

    override fun initView() {
      viewModel=getViewModel(activity!!)
      viewModel.attachView(this)
      layoutBinding.homeVm=viewModel
      sliderLayout.setScrollTimeInSec(2, ScrollTimeType.SECOND)
      sliderLayout.setPagerIndicatorVisibility(false)
      sliderLayout.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
      alcoholic_rcv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
      alcoholic_rcv.adapter=alcoholicAdapter
      non_alcoholic_rcv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
      non_alcoholic_rcv.adapter=nonAlcoholicAdapter
      viewModel.reqRandom(20)
      viewModel.reqNonAlcoholic()
      viewModel.reqAlcoholic()
    }

    override fun loadJuice(juice: Meal) {
        val  sliderView = DefaultSliderView(context!!)
        sliderView.imageUrl = juice.strMealThumb
        sliderView.setOnSliderClickListener {
            MealDetailsActivity.start(
                context!!,
                juice.idMeal,FoodType.Drinks
            )
        }
        sliderLayout.addSliderView(sliderView)
    }

    override fun loadAlcoholic(data: ArrayList<Meal>) {
       alcoholicAdapter.setData(data)
    }

    override fun loadNonAlcoholic(data: ArrayList<Meal>) {
      nonAlcoholicAdapter.setData(data)
    }

    override fun onDrinkClick(meal: Meal) {
        MealDetailsActivity.start(
            context!!,
            meal.idMeal,FoodType.Drinks
        )
    }

    override fun getJuiceFragment(): JuiceHomeFragment = this

    inline fun<reified T:BaseViewModel<JuiceHomeCallBack>> getViewModel(activity:FragmentActivity):T{
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }
}