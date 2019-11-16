package com.example.foodmeals.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodmeals.R
import com.example.foodmeals.callBacks.MealDetailsCallBack
import com.example.foodmeals.data.models.FiltersType
import com.example.foodmeals.data.models.FoodType
import com.example.foodmeals.data.models.Ingredient
import com.example.foodmeals.data.models.Meal
import com.example.foodmeals.databinding.MealDeatailStartLayoutBinding
import com.example.foodmeals.ui.adapters.IngredientAdapter
import com.example.foodmeals.ui.base.BaseActivity
import com.example.foodmeals.utils.FOOD_TYPE
import com.example.foodmeals.utils.MEAL_ID
import com.example.foodmeals.viewModels.MealDetailsViewModel
import com.example.foodmeals.viewModels.base.BaseViewModel
import com.example.foodmeals.viewModels.base.BaseViewModelFactory
import kotlinx.android.synthetic.main.meal_deatail_start_layout.*
import kotlinx.android.synthetic.main.meal_details_end_layout.ingreddient_rcv
import org.koin.android.ext.android.inject


/*
* Created By mabrouk on 12/09/19
* Cook Meals
*/

class MealDetailsActivity : BaseActivity() , MealDetailsCallBack, IngredientAdapter.IngredientListener {
    val factory:BaseViewModelFactory by inject<BaseViewModelFactory>()
    lateinit var layoutBinding: MealDeatailStartLayoutBinding
    lateinit var viewModel: MealDetailsViewModel<MealDetailsCallBack>
    lateinit var type: FoodType
    val adapter:IngredientAdapter by lazy {
        IngredientAdapter(this)
    }
    companion object{
        fun start(context: Context,id:Long,type:FoodType){
            val intent = Intent(context,MealDetailsActivity::class.java)
            intent.putExtra(MEAL_ID,id)
            intent.putExtra(FOOD_TYPE,type)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding=DataBindingUtil.setContentView(this,R.layout.meal_deatail_start_layout)
        initView()
    }

    override fun initView() {
        ingreddient_rcv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        ingreddient_rcv.adapter=adapter
        viewModel=getViewModel(this,factory)
        viewModel.attachView(this)
        type=intent.getSerializableExtra(FOOD_TYPE) as FoodType
        if (type==FoodType.Meals) {
            viewModel.reqMeal(intent.getLongExtra(MEAL_ID, 0))
        }else {
            youtube.visibility=View.GONE
            link.visibility=View.GONE
            viewModel.reqJuice(intent.getLongExtra(MEAL_ID, 0))
        }
        layoutBinding.mealsVm=viewModel
        layoutBinding.hide= type==FoodType.Drinks
    }

    override fun loadMeal(meal: Meal) {
        val ingredient = getIngredient(meal)
        Log.d("wewewe", "$ingredient")
        adapter.setData(ingredient)
        layoutBinding.meals = meal
    }


   private fun getIngredient(meal:Meal) : ArrayList<Ingredient>{
        val ingredients:ArrayList<Ingredient> = ArrayList()
        meal.strIngredient1?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure1!!)) }
        meal.strIngredient2?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure2!!)) }
        meal.strIngredient3?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure3!!)) }
        meal.strIngredient4?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure4!!)) }
        meal.strIngredient5?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure5!!)) }
        meal.strIngredient6?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure6!!)) }
        meal.strIngredient7?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure7!!)) }
        meal.strIngredient8?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure8!!)) }
        meal.strIngredient9?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure9!!)) }
        meal.strIngredient10?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure10!!)) }
        meal.strIngredient11?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure11!!)) }
        meal.strIngredient12?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure12!!)) }
        meal.strIngredient13?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure13!!)) }
        meal.strIngredient14?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure14!!)) }
        meal.strIngredient15?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure15!!)) }
        meal.strIngredient16?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure16!!)) }
        meal.strIngredient17?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure17!!)) }
        meal.strIngredient18?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure18!!)) }
        meal.strIngredient19?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure19!!)) }
        meal.strIngredient20?.apply { if (this.isNotEmpty()) ingredients.add(Ingredient(content = this,desc = meal.strMeasure20!!)) }
       return ingredients
    }

    override fun onIngredientClick(ingredient: Ingredient) {
     MealsActivity.start(this,ingredient.content,FiltersType.Ingredient,type)
    }

    inline fun <reified T : BaseViewModel<MealDetailsCallBack>> getViewModel(activity:FragmentActivity,factory:BaseViewModelFactory):T{
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }

    override fun getMealActivity(): MealDetailsActivity = this

}