package com.example.foodmeals.utils.executors

import java.util.concurrent.Executor
import javax.inject.Inject


/*
* Created By mabrouk on 19/03/19
* KotilnApp
*/

class DiskIOThreadExecutor @Inject constructor(val mDiskIO: Executor) : Executor {
    override fun execute(command: Runnable) {
        mDiskIO.execute(command)
    }
}
