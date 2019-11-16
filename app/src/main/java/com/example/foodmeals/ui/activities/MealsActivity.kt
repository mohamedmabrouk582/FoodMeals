package com.example.foodmeals.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.MealsCallBack
import com.example.foodmeals.data.models.FiltersType
import com.example.foodmeals.data.models.FoodType
import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.databinding.MealsLayoutBinding
import com.example.foodmeals.ui.adapters.FilterMealsAdapter
import com.example.foodmeals.ui.base.BaseActivity
import com.example.foodmeals.utils.FILTER_TYPE_KEY
import com.example.foodmeals.utils.FOOD_TYPE
import com.example.foodmeals.utils.GridAutoFitLayoutManager
import com.example.foodmeals.utils.QUERY_KEY
import com.example.foodmeals.viewModels.MealsViewModel
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.example.foodmeals.viewModels.base.BaseViewModelFactory
import kotlinx.android.synthetic.main.meals_layout.*
import org.koin.android.ext.android.inject


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class MealsActivity : BaseActivity() , MealsCallBack{
    val factory:BaseViewModelFactory by inject<BaseViewModelFactory>()
    lateinit var layoutBinding: MealsLayoutBinding
     lateinit var viewModel: MealsViewModel<MealsCallBack>
    lateinit var query:String
    lateinit var type: FiltersType
    lateinit var foodType: FoodType
    lateinit var searchView:SearchView
    val adapter:FilterMealsAdapter by lazy {
        FilterMealsAdapter(object : FilterMealsAdapter.MealListener{
            override fun onMealClick(meal: Meal) {
              MealDetailsActivity.start(this@MealsActivity,meal.idMeal,foodType)
            }
        })
    }

    companion object{
        fun start(context: Context,query:String, type:FiltersType,foodType: FoodType){
            val intent = Intent(context,MealsActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(QUERY_KEY,query)
            intent.putExtra(FILTER_TYPE_KEY,type)
            intent.putExtra(FOOD_TYPE,foodType)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding=DataBindingUtil.setContentView(this, R.layout.meals_layout)
        initView()
    }


    override fun initView() {
        type=intent.getSerializableExtra(FILTER_TYPE_KEY) as FiltersType
        foodType=intent.getSerializableExtra(FOOD_TYPE) as FoodType
        intent.getStringExtra(QUERY_KEY)?.apply { query=this }
        supportActionBar.apply { title=if (type!=FiltersType.Search)"$query ${foodType.name}" else type.name;this!!.setDisplayHomeAsUpEnabled(true) }
        meals_rcv.layoutManager= GridAutoFitLayoutManager(this,170)
        meals_rcv.adapter=adapter
        viewModel=getViewModel(this,factory)
        viewModel.attachView(this)
        layoutBinding.mealsVm=viewModel
        if (foodType==FoodType.Drinks)
            viewModel.reqDrink(query,type)
        else
            viewModel.reqMeals(query,type)
    }


    override fun loadMeals(data: ArrayList<Meal>) {
       adapter.setData(data)
    }

    override fun error(msg: String) {
        adapter.setData(ArrayList())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (type!=FiltersType.Search)return false
         menuInflater.inflate(R.menu.menu,menu)
         searchView =menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(viewModel.listener)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
         finish()
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//
//        }
//        return super.onOptionsItemSelected(item)
//    }

    inline fun <reified T : BaseViewModel<MealsCallBack>> getViewModel(activity: FragmentActivity, factory: BaseViewModelFactory) : T{
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }


}