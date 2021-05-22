package com.bugull.walle.action.content.logger

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.OvershootInterpolator
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bugull.walle.R
import com.bugull.walle.WalleReal
import com.bugull.walle.action.base.BaseActionView
import com.bugull.walle.ext.export2File
import com.bugull.walle.ext.screenSize
import com.bugull.walle.util.ShadowUtils

/**
 * Author by xpl, Date on 2021/5/7.
 */
class LoggerActionView() : BaseActionView(), LoggerManager.OnLogcatListener {
    private lateinit var mRecyclerView: RecyclerView
    private val mList: MutableList<LogLine> = mutableListOf()
    private val mAdapter: LogcatAdapter = LogcatAdapter()
    private var mTagFilter: TagFilter = TagFilter("")
    private var mLevelFilter: LevelFilter = LevelFilter(Log.VERBOSE)
    private var mAutoScrollToBottom = true
    private var mMaximize = true

    override val back: Boolean
        get() = true

    override fun onBackPressed(): Boolean {
        return if (mMaximize) {
            mMaximize = false
            maximizeChanged(mMaximize)
            true
        } else {
            false
        }
    }

    override fun onCreate(context: Context) {
        super.onCreate(context)
        LoggerManager.addOnLogcatListener(this)
    }

    override fun onCreateView(context: Context, root: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.action_logger, root, false)
    }

    override fun onViewCreated(root: ViewGroup) {
        super.onViewCreated(root)
        LoggerManager.start()
        with(root) {
            findViewById<ViewGroup>(R.id.content_logger)?.apply {
                setBackgroundResource(R.drawable.bg_walle)
                ShadowUtils.apply(
                    this, ShadowUtils.Config()
                        //.setCircle()
                        .setShadowColor(0xc0ff9988.toInt(), 0x60ffffff)
                )
            }

            findViewById<View>(R.id.action_clear)?.apply {
                setOnClickListener {
                    mList.clear()
                    updateData(true)
                }
            }

            findViewById<View>(R.id.action_share)?.apply {
                setOnClickListener {
                    export2File(this.context, mList.toList())
                }
            }

            mHandler.postDelayed({ updateFlag(true) }, 100)

            findViewById<EditText>(R.id.et_search).apply {
                addTextChangedListener {
                    doOnTextChanged { text, _, _, _ ->
                        mTagFilter.tag = text.toString()
                        updateData(true)
                    }
                }
            }


            // level
            findViewById<View>(R.id.filter_v).setOnClickListener {
                mLevelFilter.level = Log.VERBOSE
                updateData(true)
            }

            findViewById<View>(R.id.filter_i).setOnClickListener {
                mLevelFilter.level = Log.INFO
                updateData(true)
            }

            findViewById<View>(R.id.filter_d).setOnClickListener {
                mLevelFilter.level = Log.DEBUG
                updateData(true)
            }

            findViewById<View>(R.id.filter_w).setOnClickListener {
                mLevelFilter.level = Log.WARN
                updateData(true)
            }

            findViewById<View>(R.id.filter_e).setOnClickListener {
                mLevelFilter.level = Log.ERROR
                updateData(true)
            }

            // RecyclerView
            mRecyclerView = findViewById<RecyclerView>(R.id.recycler_logger).apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.bottom = 1
                    }
                })
                addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        (recyclerView.layoutManager as? LinearLayoutManager)?.run {
                            mAutoScrollToBottom =
                                findLastCompletelyVisibleItemPosition() == recyclerView.adapter!!.itemCount - 1
                        }
                    }
                })
                adapter = mAdapter

            }

            findViewById<View>(R.id.iv_scroll_top).setOnClickListener {
                scrollToTop()
            }

            findViewById<View>(R.id.iv_scroll_bottom).setOnClickListener {
                scrollToBottom()
            }

            // bottom
            findViewById<View>(R.id.iv_close).setOnClickListener {
                WalleReal.dismiss(this@LoggerActionView)
            }

            findViewById<TextView>(R.id.tv_title).apply {
                setText(R.string.action_name_logger)
            }

            findViewById<ImageView>(R.id.iv_back).apply {
                setImageResource(R.drawable.ic_minimize)
                setOnClickListener {
                    if (mMaximize) {
                        mMaximize = false
                        maximizeChanged(mMaximize)
                    }
                }
            }

            setOnClickListener {
                if (!mMaximize) {
                    mMaximize = true
                    maximizeChanged(mMaximize)
                }
            }
        }
    }

    private fun updateData(immediately: Boolean) {
        val show = mList.toList().filter {
            mLevelFilter.validate(it) && mTagFilter.validate(it)
        }
        mHandler.postDelayed(
            {
                onLogChangedAnimator()
                mAdapter.setData(show, mTagFilter.tag)
                if (mAutoScrollToBottom) {
                    scrollToBottom()
                }
            }, if (immediately) {
                DELAY_UPDATE_DATA
            } else {
                DELAY_UPDATE_DATA * 2
            }
        )
    }

    private fun maximizeChanged(max: Boolean) {
        this.mRootView?.run {
            mHandler.postDelayed({ updateFlag(max) }, 100)
            val visible = if (max) View.VISIBLE else View.GONE
            val invisible = if (max) View.GONE else View.VISIBLE
            findViewById<View>(R.id.bottom).visibility = visible
            findViewById<View>(R.id.top_logger).visibility = visible
            findViewById<View>(R.id.rg_level).visibility = visible
            findViewById<View>(R.id.fl_content).visibility = visible
            findViewById<View>(R.id.iv_logger).visibility = invisible

            findViewById<ViewGroup>(R.id.content_logger).apply {
                setBackgroundResource(if (max) R.drawable.bg_walle else R.drawable.bg_transparent)
                ShadowUtils.apply(
                    this, ShadowUtils.Config()
                        //.setCircle()
                        .setShadowColor(0xc0ff9988.toInt(), 0x60ffffff)
                )
            }
            when (val params = this.layoutParams) {
                is WindowManager.LayoutParams -> {
                    if (max) {
                        maximize(params)
                    } else {
                        minimize(params)
                    }
                    WalleReal.getWindowManager().updateViewLayout(this, params)
                }

                is FrameLayout.LayoutParams -> {

                }
            }
        }
    }

    private var mAnimator: Animator? = null

    private fun onLogChangedAnimator() {
        if (mMaximize) return
        //mAnimator?.cancel()
        this.mRootView?.findViewById<ImageView>(R.id.iv_logger)?.let { iv ->
            mAnimator = ValueAnimator.ofFloat(1f, 0.5f, 1f).apply {
                duration = 300
                addUpdateListener {
                    val value = it.animatedValue as Float
                    iv.scaleX = value
                    iv.scaleY = value
                    interpolator = OvershootInterpolator(1f)
                    //iv.rotationY = 360 * animatedFraction
                }
            }
            mAnimator?.start()
        }
    }

    private fun scrollToBottom() {
        mRecyclerView.scrollToPosition(mAdapter.itemCount - 1)
    }

    private fun scrollToTop() {
        mRecyclerView.scrollToPosition(0)
    }


    override fun onDestroy() {
        super.onDestroy()
        LoggerManager.cancel()
        mHandler.removeCallbacksAndMessages(null)
        mAnimator?.cancel()
        mAnimator = null
    }


    override fun initLayoutParams(params: ViewGroup.LayoutParams) {
        super.initLayoutParams(params)
        when (params) {
            is WindowManager.LayoutParams -> {
                maximize(params)
            }
        }
    }

    private fun maximize(params: WindowManager.LayoutParams) {
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = mRootView?.context?.screenSize?.y?.run {
            this * 2 / 3
        } ?: WindowManager.LayoutParams.WRAP_CONTENT
        val oldX = params.x
        val oldY = params.y

        params.x = 0
        params.y = 0
    }

    private fun minimize(params: WindowManager.LayoutParams) {
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.x = 0
        params.y = 0
    }


    override fun onCatch(line: LogLine) {
        mList.add(line)
        updateData(false)
        /* mHandler.post {
             mAdapter.addData(line)
             scrollToBottom()
         }*/
        /* mList.add(line)
         //mRecyclerView.adapter = LogcatAdapter(mList)
         mAdapter.notifyDataSetChanged()*/
    }

    companion object {
        const val DELAY_UPDATE_DATA = 100L

    }

}

