package com.bugull.walle.action.core

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Author by xpl, Date on 2021/5/6.
 */
interface ActionViewManager {

    fun show(actionInfo: ActionInfo)

    fun dismiss(actionView: AbsActionView)

    fun dismiss(tag: String)

    fun dismiss(clz: Class<out AbsActionView>)

    fun dismissAll()

    fun update(view: AbsActionView, params: ViewGroup.LayoutParams)

    fun hasShow(clazz: Class<out AbsActionView>):Boolean

//    fun getActionView(tag: String): AbsActionView
//
//    fun notifyBackground()
//
//    fun getActionViews(): Map<String, AbsActionView>

//    fun notifyForeground()

//    fun onActivityCreate(activity: Activity)
//
//    fun onActivityResume(activity: Activity)
//
//    fun onActivityPause(activity: Activity)
//
//    fun onActivityDestroy(activity: Activity)


}