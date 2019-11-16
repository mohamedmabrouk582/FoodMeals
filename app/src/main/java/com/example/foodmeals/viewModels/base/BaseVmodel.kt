package com.example.foodmeals.viewModels.base

import com.example.foodmeals.callBacks.BaseCallBack


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

interface BaseVmodel<v : BaseCallBack> {
    fun attachView(view: v)
}
