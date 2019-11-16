package com.example.foodmeals.callBacks

import com.example.foodmeals.data.models.Meal

interface JuiceHomeCallBack : BaseCallBack {
  fun loadJuice(juice:Meal)
  fun loadAlcoholic(data:ArrayList<Meal>)
  fun loadNonAlcoholic(data: ArrayList<Meal>)
}