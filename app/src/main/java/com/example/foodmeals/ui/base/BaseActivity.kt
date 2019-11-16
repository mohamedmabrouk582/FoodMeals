package com.example.foodmeals.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.example.foodmeals.R
import kotlinx.android.synthetic.main.snackbar_view.*


/*
* Created By mabrouk on 19/03/19
* KotilnApp
*/

open abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun initView()

    fun hideSoftKeyBoard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = currentFocus
        view = view ?: View(this)
        imm.let {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showSoftKeyBoard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.let {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    fun replaceFragment(
        fragment: Fragment, @IdRes container: Int,
        addToBackStack: Boolean = false,
        allowStateLoss: Boolean = false,
        tag: String = fragment.javaClass.name
    ) {
        if (!supportFragmentManager.popBackStackImmediate(tag, 0)) {
            val transAction = supportFragmentManager.beginTransaction()
            transAction.replace(container, fragment)

            if (addToBackStack)
                transAction.addToBackStack(tag)
            if (allowStateLoss)
                transAction.commitAllowingStateLoss() else transAction.commit()
        }
    }

    @SuppressLint("ResourceType")
    fun showSnackBar(msg: String, @IdRes image: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        val snackBar: Snackbar = Snackbar.make(findViewById(android.R.id.content), "", duration)
        val layout: Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        val textView: TextureView = layout.findViewById(R.id.snackbar_text)
        textView.visibility = View.INVISIBLE

        // custom  snack  view
        val snackView: View = LayoutInflater.from(this).inflate(R.layout.snackbar_view, null, false)
        snack_img.setImageResource(image)
        snack_msg.text = msg
        snack_msg.setTextColor(android.R.color.white)
        layout.addView(snackView, 0)
        snackBar.show()
    }

    fun calculateNoOfColumns(columnWidthDp: Float): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

}
