package com.example.foodmeals.utils.network

import android.content.Context
import io.reactivex.observers.DisposableObserver
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue
import androidx.lifecycle.MutableLiveData
import com.example.foodmeals.R
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

class Request<t>(var context: Context, var listener: RequestListener<t>) : DisposableObserver<t>() {

    override fun onNext(t: t) {
        if (t == null) {
            listener.onEmpty(context.getString(R.string.no_data_found))
        } else {
            val data = MutableLiveData<t>()
            data.value = t
            listener.onResponse(data)
        }
    }

    override fun onError(e: Throwable) {
        if (!CheckNetwork.isConnected(context)) {
            listener.onNetWorkError(context.getString(R.string.no_internet_connection))
        } else {
            when (e) {

                is HttpException -> {
                    val error: String = analysisError(e)
                    when (e.code()) {
                        401, 403 -> listener.onSessionExpired(error)
                        else -> listener.onError(error)
                    }
                }
                is SocketTimeoutException -> listener.onError(context.getString(R.string.socketTimeout))
                is JsonSyntaxException -> listener.onError(context.getString(R.string.jsonError))
                is SSLHandshakeException -> listener.onError(e.message.toString())
                else -> listener.onError("Api Error")
            }
        }
    }

    private fun analysisError(e: HttpException): String {
        try {
            val responseStrings: String = e.response()!!.errorBody().toString()
            val jsonObject = JSONObject(responseStrings)
            return when {
                jsonObject.has("msg") -> jsonObject.get("msg").toString()
                jsonObject.has("error") -> jsonObject.get("error").toString()
                else -> e.message()
            }
        } catch (ex: Exception) {
            return e.message()
        }
    }

    override fun onComplete() {

    }
}
