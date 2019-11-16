package com.example.foodmeals.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foodmeals.ui.fragments.JuiceHomeFragment
import com.example.foodmeals.ui.fragments.MealsHomeFragment

class HomeAdapter(fm:FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment =
        when(position){
          0 -> MealsHomeFragment()
          1 -> JuiceHomeFragment()
          else -> MealsHomeFragment()
      }

}