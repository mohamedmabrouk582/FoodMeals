package com.example.foodmeals.utils.network

import android.net.NetworkInfo
import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

class CheckNetwork {
    companion object {
        fun isConnected(context: Context): Boolean {
            try {
                val connMgr =
                    context.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connMgr.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            } catch (e: Exception) {
                return false
            }

        }

        fun isConnected(context: Context, OnCheckConnection: OnCheckConnection) {
            if (isConnected(context)) {
                OnCheckConnection.ConnectionTrue()
            } else {
                OnCheckConnection.ConnectionError()
            }
        }
    }
}
