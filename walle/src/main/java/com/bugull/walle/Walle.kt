package com.bugull.walle

import android.app.Application

/**
 * Author by xpl, Date on 2021/5/6.
 */
object Walle {
    @JvmStatic
    fun init(application: Application, config: Configuration? = null) {
        WalleReal.init(application)
    }

    @JvmStatic
    fun show() {
        WalleReal.show()
    }

    @JvmStatic
    fun hide() {
        WalleReal.hide()
    }

    @JvmStatic
    fun showHome() {
        WalleReal.showHome()
    }

    @JvmStatic
    fun hideHome() {
        WalleReal.hideHome()
    }

    @JvmStatic
    fun close() {

    }

    @JvmStatic
    fun addGroup(){

    }
}



