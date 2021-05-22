package com.bugull.walle.action.core

import android.content.Context
import android.view.ViewGroup
import android.view.WindowManager

/**
 * Author by xpl, Date on 2021/5/6.
 */
class NormalActionViewManager(private val context: Context) : ActionViewManager {
    private val mWindowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    override fun show(actionInfo: ActionInfo) {

    }

    override fun dismiss(actionView: AbsActionView) {
    }

    override fun dismiss(tag: String) {
    }

    override fun dismiss(clz: Class<out AbsActionView>) {

    }

    override fun dismissAll() {
    }


    override fun update(view: AbsActionView, params: ViewGroup.LayoutParams) {
    }

    override fun hasShow(clazz: Class<out AbsActionView>): Boolean {
        return false
    }

}