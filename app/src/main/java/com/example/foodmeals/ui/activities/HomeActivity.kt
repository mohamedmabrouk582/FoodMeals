package com.example.foodmeals.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.foodmeals.R
import com.example.foodmeals.databinding.HomeLayoutBinding
import com.example.foodmeals.ui.adapters.HomeAdapter
import com.example.foodmeals.ui.base.BaseActivity
import kotlinx.android.synthetic.main.home_layout.*

class HomeActivity : BaseActivity() {
    lateinit var layoutBinding: HomeLayoutBinding
    val adapter : HomeAdapter by lazy { HomeAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding=DataBindingUtil.setContentView(this, R.layout.home_layout)
        initView()
    }

    override fun initView() {

      home_view_pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
      home_view_pager.adapter = adapter
      home_view_pager.isUserInputEnabled=false
      home_nav.setOnNavigationItemSelectedListener { item ->
          when(item.itemId){
              R.id.cook_item -> home_view_pager.currentItem=0
              R.id.juice_item -> home_view_pager.currentItem=1
          }
          return@setOnNavigationItemSelectedListener true
      }
    }
}