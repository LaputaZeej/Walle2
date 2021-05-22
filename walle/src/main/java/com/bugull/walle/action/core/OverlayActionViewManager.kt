package com.bugull.walle.action.core

import android.content.Context
import android.view.ViewGroup
import android.view.WindowManager
import com.bugull.android.basic.removeCondition
import com.bugull.walle.OverlayType
import com.bugull.walle.ext.canOverlay
import com.bugull.walle.ext.gotoOverlay

/**
 * Author by xpl, Date on 2021/5/6.
 */
class OverlayActionViewManager(private val context: Context) : ActionViewManager {

    private val mWindowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val mViews: MutableList<AbsActionView> = mutableListOf()

    override fun show(actionInfo: ActionInfo) {
        if (!context.canOverlay()) {
            context.gotoOverlay()
            return
        }
        val view = actionInfo.clazz.newInstance()
        mViews.add(view)
        view.createView(context, OverlayType.Overlay)
        mWindowManager.addView(view.mRootView, view.mParams)
        view.onResume()
    }

    override fun dismiss(actionView: AbsActionView) {
        mViews.removeCondition({
            this == actionView
        }) {
            removeView(this)
            this.onDestroy()
        }
    }

    override fun dismiss(tag: String) {
        mViews.removeCondition({
            this.tag == tag
        }) {
            removeView(this)
            this.onDestroy()
        }
    }

    override fun dismiss(clz: Class<out AbsActionView>) {
        mViews.removeCondition({
            javaClass == clz
        }) {
            removeView(this)
            this.onDestroy()
        }
    }

    override fun dismissAll() {
        mViews.removeCondition {
            removeView(this)
            this.onDestroy()
        }
    }

    private fun removeView(view: AbsActionView) {
        if (view.mRootView?.isAttachedToWindow == true) {
            mWindowManager.removeView(view.mRootView)
        }
    }

    override fun update(view: AbsActionView, params: ViewGroup.LayoutParams) {
        mWindowManager.updateViewLayout(view.mRootView, params)
    }

    override fun hasShow(clazz: Class<out AbsActionView>): Boolean {
        return mViews.any {
            it.javaClass == clazz
        }
    }


}