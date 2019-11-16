package com.example.foodmeals.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.example.foodmeals.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.snackbar_view.*


/*
* Created By mabrouk on 19/03/19
* KotilnApp
*/

open abstract class BaseBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        inject()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected abstract fun inject()

    protected abstract fun initView()


    fun hideSoftKeyBoard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity?.currentFocus
        view = view ?: View(context)
        imm.let {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showSoftKeyBoard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.let {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    @SuppressLint("ResourceType")
    fun showSnackBar(msg: String, @IdRes image: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        if (view != null) {
            val snackBar: Snackbar = Snackbar.make(view!!, "", duration)
            val layout: Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout
            val textView: TextureView = layout.findViewById(R.id.snackbar_text)
            textView.visibility = View.INVISIBLE

            // custom  snack  view
            val snackView: View =
                LayoutInflater.from(context).inflate(R.layout.snackbar_view, null, false)
            snack_img.setImageResource(image)
            snack_msg.text = msg
            snack_msg.setTextColor(android.R.color.white)
            layout.addView(snackView, 0)
            snackBar.show()
        } else {
            Toast.makeText(context, msg, duration).show()
        }
    }

    fun replaceFragment(
        fragment: Fragment, @IdRes container: Int,
        addToBackStack: Boolean = false,
        allowStateLoss: Boolean = false,
        tag: String = fragment.javaClass.name
    ) {
        if (!fragmentManager!!.popBackStackImmediate(tag, 0)) {
            val transAction = fragmentManager!!.beginTransaction()
            transAction.replace(container, fragment)

            if (addToBackStack)
                transAction.addToBackStack(tag)
            if (allowStateLoss)
                transAction.commitAllowingStateLoss() else transAction.commit()
        }
    }
}
