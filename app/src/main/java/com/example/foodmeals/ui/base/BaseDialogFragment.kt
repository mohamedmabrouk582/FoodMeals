package com.example.foodmeals.ui.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.example.foodmeals.R
import kotlinx.android.synthetic.main.snackbar_view.*
import java.util.*


/*
* Created By mabrouk on 19/03/19
* KotilnApp
*/

open abstract class BaseDialogFragment : DialogFragment() {
    protected lateinit var dialog: AlertDialog
    protected lateinit var currentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        currentView = LayoutInflater.from(context).inflate(getLayoutResource(), null, false)
        val builder = AlertDialog.Builder(context!!)
        builder.setView(currentView)
        dialog = builder.create()
        initView()
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }


    protected abstract fun initView()

    protected abstract fun getLayoutResource(): Int

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

}
