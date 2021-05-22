package com.bugull.walle.action.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import com.bugull.walle.*
import com.bugull.walle.ext.isPortrait
import com.bugull.walle.ext.logv
import com.bugull.walle.ext.longSideLength
import com.bugull.walle.ext.shortSideLength

/**
 * Author by xpl, Date on 2021/5/6.
 */
interface ActionView {
    fun onCreate(context: Context)

    fun onCreateView(context: Context, root: ViewGroup): View

    fun onViewCreated(root: ViewGroup)

    fun onResume()

    fun onPause()

    fun onDestroy()

    fun onBackground()

    fun onForeground()

    fun initLayoutParams(params: ViewGroup.LayoutParams)

    val drag: Boolean

    val back: Boolean

    fun onBackPressed():Boolean

    var focus: Boolean


}

abstract class AbsActionView : ActionView, OnAppChangedListener, TouchProxy.OnTouchEventListener {

    val tag: String = this.javaClass.name
    var mRootView: RootView? = null
    var mParams: ViewGroup.LayoutParams? = null

    private var mViewHeight: Int = 0
    private var mViewWidth: Int = 0
    private var mViewTreeObserver: ViewTreeObserver? = null
    private val s: ViewTreeObserver.OnGlobalLayoutListener =
        ViewTreeObserver.OnGlobalLayoutListener {
            mRootView?.run {
                mViewWidth = measuredWidth
                mViewHeight = measuredHeight
                //
            }
        }

    private val mTouchProxy: TouchProxy = TouchProxy(this)

    override var focus: Boolean = true
    override val drag: Boolean = true
    override val back: Boolean = false

    override fun onBackPressed():Boolean {
        return false
    }

    override fun onCreate(context: Context) {
        if (DEBUG) logv("$SUB_TAG::onCreate ${hashCode()}")
        WalleReal.addOnAppChangedListener(this)
    }




    override fun onViewCreated(root: ViewGroup) {
        if (DEBUG) logv("$SUB_TAG::onViewCreated ${hashCode()}")
    }

    override fun onResume() {
        if (DEBUG) logv("$SUB_TAG::onResume ${hashCode()}")
    }

    override fun onPause() {
        if (DEBUG) logv("$SUB_TAG::onPause ${hashCode()}")
    }

    override fun onDestroy() {
        if (DEBUG) logv("$SUB_TAG::onDestroy ${hashCode()}")
        WalleReal.removeOnAppChangedListener(this)
        removeViewTreeObserverListener()
        mRootView = null
        mParams = null
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onBackground() {
        if (DEBUG) logv("$SUB_TAG::onBackground ${hashCode()}")

        mRootView?.visibility = View.GONE
    }

    override fun onForeground() {
        if (DEBUG) logv("$SUB_TAG::onForeground ${hashCode()}")
        mRootView?.visibility = View.VISIBLE
    }

    override fun initLayoutParams(params: ViewGroup.LayoutParams) {
    }

    @SuppressLint("ClickableViewAccessibility")
    internal fun createView(context: Context, overlayType: OverlayType) {
        onCreate(context)
        mRootView = object :RootView(context){
            override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

                if (KeyEvent.ACTION_UP == event?.action && back){
                    if (event.keyCode == KeyEvent.KEYCODE_BACK || event.keyCode == KeyEvent.KEYCODE_BACK ){
                        return onBackPressed()
                    }
                }

                return super.dispatchKeyEvent(event)

            }
        }
        mRootView?.let { root ->
            val child = onCreateView(context, root)
            root.addView(child)
            root.setOnTouchListener { v, event ->
                mTouchProxy.onTouchEvent(v, event)
            }
            addViewTreeObserverListener()
            onViewCreated(root)
            mParams = when (overlayType) {
                OverlayType.Overlay -> {
                    WindowManager.LayoutParams().apply {
                        type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                        } else {
                            WindowManager.LayoutParams.TYPE_PHONE
                        }

                        if (focus) {
                            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        }
/*
                        flags =
                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS*/
                        /* flags = (flags
                                 or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                 or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                                 )*/

                        format = PixelFormat.TRANSLUCENT
                        gravity = Gravity.LEFT or Gravity.TOP
                        width = WindowManager.LayoutParams.WRAP_CONTENT
                        height = WindowManager.LayoutParams.WRAP_CONTENT
                        x = 0
                        y = 0
                    }
                }
                OverlayType.Normal -> {
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        gravity = Gravity.LEFT or Gravity.TOP
                    }
                }
                //else -> throw IllegalStateException("错误的ActionView")
            }.apply {
                initLayoutParams(this)
            }

        } ?: throw IllegalStateException("无法创建RootView")


    }

    /**
     * 解决悬浮窗口键盘不能编辑的问题
     * 改变flag
     */
    protected fun updateFlag(enable: Boolean, block: (Block)? = null) {
        mRootView?.run {

            if (!this.isAttachedToWindow) {
                return
            }
            val layout = this.layoutParams.run {
                this@run as WindowManager.LayoutParams
            }.also {
                mHandler.removeMessages(MSG_UPDATE_FLAG)
                if (enable) {
                    // 恢复
//                    mHandler.sendMessageDelayed(Message.obtain().apply {
//                        what = MSG_UPDATE_FLAG
//                        obj = block
//                    }, UPDATE_FLAG_DELAY)
                    it.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                } else {
                    block?.invoke()
                    it.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                }
            }
            post {
                WalleReal.getWindowManager().updateViewLayout(this, layout)
            }
        }

    }

    protected val mHandler = Handler() {
        when (it.what) {
            MSG_UPDATE_FLAG -> {
                @Suppress("UNCHECKED_CAST") val block: Block? = it.obj as Block?
                block?.invoke()
                updateFlag(false)
            }
        }
        false
    }

    private fun addViewTreeObserverListener() {
        if (mViewTreeObserver == null && mRootView != null) {
            mViewTreeObserver = mRootView?.viewTreeObserver
            mViewTreeObserver?.addOnGlobalLayoutListener(s)
        }
    }

    private fun removeViewTreeObserverListener() {
        mViewTreeObserver?.takeIf {
            it.isAlive
        }?.run {
            removeOnGlobalLayoutListener(s)
        }
    }

    override fun onMove(x: Int, y: Int, dx: Int, dy: Int) {
        if (!drag) {
            return
        }
        when (mParams) {
            is WindowManager.LayoutParams -> {
                (mParams as WindowManager.LayoutParams).apply {
                    this.x += dx
                    this.y += dy
                }

                WalleReal.getWindowManager().updateViewLayout(mRootView, mParams)
            }
            is FrameLayout.LayoutParams -> {
                (mParams as FrameLayout.LayoutParams).apply {
                    this.leftMargin += dx
                    this.topMargin += dy
                    resetBorderline(this)
                }

            }
        }

    }

    override fun onDown(x: Int, y: Int) {
        if (!drag) {
            return
        }
    }

    override fun onUp(x: Int, y: Int) {
        if (!drag) {
            return
        }

    }

    override fun onChanged(status: Status) {
        when (status) {
            Status.Background -> onBackground()
            Status.Foreground -> onForeground()
        }
    }

    var restrictBorderline: Boolean = true

    /**
     * 限制边界 调用的时候必须保证是在控件能获取到宽高德前提下
     */
    private fun resetBorderline(normalFrameLayoutParams: FrameLayout.LayoutParams) {
        //如果是系统模式或者手动关闭动态限制边界
        if (!restrictBorderline || Configuration.overlayType == OverlayType.Normal) {
            return
        }
        //LogHelper.i(TAG, "topMargin==>" + normalFrameLayoutParams.topMargin + "  leftMargin====>" + normalFrameLayoutParams.leftMargin);
        if (normalFrameLayoutParams.topMargin <= 0) {
            normalFrameLayoutParams.topMargin = 0
        }

        mRootView?.context?.run {
            if (isPortrait) {
                if (normalFrameLayoutParams.topMargin >= longSideLength - mViewHeight) {
                    normalFrameLayoutParams.topMargin = longSideLength - mViewHeight
                }
            } else {
                if (normalFrameLayoutParams.topMargin >= shortSideLength - mViewHeight) {
                    normalFrameLayoutParams.topMargin =
                        shortSideLength - mViewHeight
                }
            }
            if (normalFrameLayoutParams.leftMargin <= 0) {
                normalFrameLayoutParams.leftMargin = 0
            }
            if (isPortrait) {
                if (normalFrameLayoutParams.leftMargin >= shortSideLength - mViewWidth) {
                    normalFrameLayoutParams.leftMargin =
                        shortSideLength - mViewWidth
                }
            } else {
                if (normalFrameLayoutParams.leftMargin >= longSideLength - mViewWidth) {
                    normalFrameLayoutParams.leftMargin = longSideLength - mViewWidth
                }
            }
        }

    }

    internal companion object {
        private const val SUB_TAG = "AbsActionView"
        private const val DEBUG = true

        const val MSG_UPDATE_FLAG = 0X02
        const val UPDATE_FLAG_DELAY = 12 * 1000L
    }

    open class RootView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : FrameLayout(context, attrs, defStyleAttr)
}

typealias Block = () -> Unit