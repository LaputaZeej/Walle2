package com.bugull.walle.action.content.home

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Author by xpl, Date on 2021/5/6.
 */
interface Action {

    val group: Group

    @get:StringRes
    val name: Int

    @get:DrawableRes
    val icon: Int

    val step: Step
        get() = Step.NAN

    fun doAction(context: Context)

    fun init(context: Context)

    sealed class Step {
        object Release : Step()
        object Dev : Step()
        object NAN : Step()
    }
}