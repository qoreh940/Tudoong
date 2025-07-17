package com.chch.tudoong

import android.app.Application
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.chch.tudoong.utils.logd
import com.chch.tudoong.widget.WidgetUpdateManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@HiltAndroidApp
class TudoongApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver(this))
    }

    private class AppLifecycleObserver(
        private val context: Context
    ) : DefaultLifecycleObserver {

        override fun onStop(owner: LifecycleOwner) {
            owner.lifecycleScope.launch{
                WidgetUpdateManager.updateIfNeeded(context)
            }
        }
    }

}