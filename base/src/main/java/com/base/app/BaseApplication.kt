package com.base.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.annotation.StringRes
import android.support.multidex.MultiDex
import android.widget.Toast
import com.arden.arch.di.DaggerBaseDaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import org.greenrobot.eventbus.EventBus

/**
 * Created by Sunjie on 2017/11/14.
 */
open class BaseApplication : DaggerApplication() {
    val metadata: Bundle? get() = applicationInfo.metaData

    val appBus: EventBus = EventBus()
    open val multiDexEnable: Boolean = true
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (multiDexEnable) MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerBaseDaggerApplicationComponent.builder().create(this)
    }

    fun getDefaultSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    open fun toast(text: CharSequence) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    open fun toast(@StringRes resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    fun post(event: Any) {
        appBus.post(event)
    }

    fun postSticky(event: Any) {
        appBus.postSticky(event)
    }
}