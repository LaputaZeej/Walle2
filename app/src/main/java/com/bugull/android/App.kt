package com.bugull.android

import android.app.Application
import com.bugull.walle.Walle

/**
 * Author by xpl, Date on 2021/5/7.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Walle.init(this)
    }
}