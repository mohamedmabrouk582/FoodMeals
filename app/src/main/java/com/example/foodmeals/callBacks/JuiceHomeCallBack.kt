package com.example.foodmeals.callBacks

import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.ui.fragments.JuiceHomeFragment

interface JuiceHomeCallBack : BaseCallBack {
  fun getJuiceFragment() : JuiceHomeFragment
  fun loadJuice(juice:Meal)
  fun loadAlcoholic(data:ArrayList<Meal>)
  fun loadNonAlcoholic(data: ArrayList<Meal>)
}