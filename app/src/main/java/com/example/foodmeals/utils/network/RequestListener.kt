package com.example.foodmeals.utils.network

import androidx.lifecycle.MutableLiveData


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

interface RequestListener<t> {
    fun onResponse(data: MutableLiveData<t>)
    fun onEmpty(msg: String){}
    fun onError(msg: String)
    fun onSessionExpired(msg: String)
    fun onNetWorkError(msg: String)
}
