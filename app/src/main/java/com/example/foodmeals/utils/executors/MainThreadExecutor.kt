package com.example.foodmeals.utils.executors

import android.os.Handler
import androidx.annotation.NonNull
import java.util.concurrent.Executor
import javax.inject.Inject


/*
* Created By mabrouk on 19/03/19
* KotilnApp
*/

class MainThreadExecutor @Inject constructor(val mainThreadHandler: Handler) : Executor {
    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}
