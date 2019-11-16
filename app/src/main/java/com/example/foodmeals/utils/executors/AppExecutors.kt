package com.example.foodmeals.utils.executors

import java.util.concurrent.Executor
import javax.inject.Inject


/*
* Created By mabrouk on 19/03/19
* KotilnApp
*/

class AppExecutors @Inject constructor(
    val diskIO: Executor,
    val networkIO: Executor,
    val mainThread: Executor
) {

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }
}
