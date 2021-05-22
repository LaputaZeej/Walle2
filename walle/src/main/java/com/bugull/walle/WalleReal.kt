package com.bugull.walle

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.ProcessLifecycleOwner
import com.bugull.walle.action.content.EnterActionView
import com.bugull.walle.action.core.*
import com.bugull.walle.action.content.home.HomeActionView
import com.bugull.walle.ext.mWindowManager

/**
 * Author by xpl, Date on 2021/5/7.
 */
@SuppressLint("StaticFieldLeak")
internal object WalleReal : ActionViewManager {
    private lateinit var mContext: Context
    private lateinit var mActionManager: ActionViewManager
    private val mWalleLifecycleObserver: WalleAppLifecycleObserver = WalleAppLifecycleObserver()
    private val mWalleActivityLifecycleCallbacks: WalleActivityLifecycleCallbacks =
        WalleActivityLifecycleCallbacks()

    fun init(application: Application) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(mWalleLifecycleObserver)
        application.registerActivityLifecycleCallbacks(mWalleActivityLifecycleCallbacks)
        this.mContext = application.applicationContext
        this.mActionManager =
            when (Configuration.overlayType) {
                OverlayType.Overlay -> {
                    OverlayActionViewManager(mContext)
                }

                OverlayType.Normal -> {
                    NormalActionViewManager(mContext)
                }
            }
    }

    fun show() {
        show(ActionInfo(EnterActionView::class.java))
    }

    fun hide() {
        dismiss(EnterActionView::class.java)
    }

    fun showHome() {
        show(ActionInfo(HomeActionView::class.java))
    }

    fun hideHome() {
        dismiss(HomeActionView::class.java)
    }

    fun addOnAppChangedListener(listener: OnAppChangedListener) {
        mWalleLifecycleObserver.addOnAppChangedListener(listener)
    }

    fun removeOnAppChangedListener(listener: OnAppChangedListener) {
        mWalleLifecycleObserver.removeOnAppChangedListener(listener)
    }

    fun clearOnAppChangedListener() {
        mWalleLifecycleObserver.clear()
    }

    fun getWindowManager() = mContext.mWindowManager

    override fun show(actionInfo: ActionInfo) {
        if (actionInfo.single){
            if (!hasShow(actionInfo.clazz)) {
                mActionManager.show(actionInfo)
            }
        }

    }


    override fun dismiss(actionView: AbsActionView) {
        mActionManager.dismiss(actionView)
    }

    override fun dismiss(tag: String) {
        mActionManager.dismiss(tag)
    }

    override fun dismiss(clz: Class<out AbsActionView>) {
        mActionManager.dismiss(clz)
    }

    override fun dismissAll() {
        mActionManager.dismissAll()
    }

    override fun update(view: AbsActionView, params: ViewGroup.LayoutParams) {
        mActionManager.update(view, params)
    }

    override fun hasShow(clazz: Class<out AbsActionView>): Boolean {
        return mActionManager.hasShow(clazz)
    }


}
