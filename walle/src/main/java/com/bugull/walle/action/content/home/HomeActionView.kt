package com.bugull.walle.action.content.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bugull.walle.Configuration
import com.bugull.walle.R
import com.bugull.walle.WalleReal
import com.bugull.walle.action.base.BaseActionView

/**
 * Author by xpl, Date on 2021/5/7.
 */
class HomeActionView() : BaseActionView() {

    private var state: State = State.Group
    private var currentGroup: Group? = null

    private val groups: MutableList<Group> = mutableListOf()
    private val actions: MutableMap<Group, List<Action>> = mutableMapOf()

    override fun onCreate(context: Context) {
        super.onCreate(context)
        initGroupsAndActions()

    }

    private fun initGroupsAndActions() {
        groups.addAll(Configuration.defaultGroupList)
        actions.putAll(Configuration.defaultActions)
        groups.addAll(Configuration.customGroupList)
        actions.putAll(Configuration.customActions)
    }

    override fun onCreateView(context: Context, root: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.action_home, root, false)
    }

    override fun onViewCreated(root: ViewGroup) {
        super.onViewCreated(root)
        with(root) {
            updateUi(state)
            findViewById<View>(R.id.iv_back).setOnClickListener {
                if (state == State.Action) {
                    state = State.Group
                    updateUi(state)
                }
            }

            findViewById<View>(R.id.iv_close).setOnClickListener {
                WalleReal.dismiss(this@HomeActionView)
            }
        }
    }

    private fun updateUi(_state: State) {
        when (_state) {
            State.Group -> {
                refreshGroup()
            }
            State.Action -> {
                refreshAction(currentGroup)
            }
        }
        mRootView?.invalidate()
    }

    private fun refreshGroup() {
        find<RecyclerView>(R.id.recycler_action)?.visibility = View.GONE
        find<RecyclerView>(R.id.recycler_group)?.apply {
            val row = ROW
            overScrollMode = OVER_SCROLL_NEVER
            layoutManager = GridLayoutManager(context, row)
            /* val gridDividerItemDecoration = GridDividerItemDecoration(context)
             addItemDecoration(gridDividerItemDecoration)*/
            adapter =
                GroupAdapter(groups, /*gridDividerItemDecoration.mDivider.intrinsicHeight*/0, row) {
                    state = State.Action
                    currentGroup = it
                    updateUi(state)
                }
            this.visibility = View.VISIBLE
            find<View>(R.id.iv_back)?.visibility = View.GONE
            find<TextView>(R.id.tv_title)?.text = "功能分类"
        }
    }

    private fun refreshAction(group: Group?) {
        val list = actions[group ?: Group.Basic]
        if (list.isNullOrEmpty()) {
            toast(R.string.str_todo)
            return
        }
        find<View>(R.id.iv_back)?.visibility = View.VISIBLE
        find<TextView>(R.id.tv_title)?.text = "功能"

        find<RecyclerView>(R.id.recycler_group)?.visibility = View.GONE
        find<RecyclerView>(R.id.recycler_action)?.apply {
            val row = ROW
            layoutManager = GridLayoutManager(context, row)
            /* val gridDividerItemDecoration = GridDividerItemDecoration(context)
             addItemDecoration(gridDividerItemDecoration)*/
            /*addItemDecoration(GridDividerItemDecorationJava(
                context,
                dp2px(2f),
                dp2px(2f),
                true,
                true,
                Color.WHITE
            ))*/
            overScrollMode = OVER_SCROLL_NEVER
            adapter = ActionAdapter(
                list,
                /* gridDividerItemDecoration.mDivider.intrinsicHeight*/0, row
            ) {
                it.doAction(mRootView?.context!!)
                WalleReal.dismiss(this@HomeActionView)
            }
            this.visibility = View.VISIBLE

        }
    }


    override fun initLayoutParams(params: ViewGroup.LayoutParams) {
        super.initLayoutParams(params)
        when (params) {
            is WindowManager.LayoutParams -> {
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
                params.x = 0
                params.y = 0
            }
        }
    }

    companion object {
        const val ROW = 3

        sealed class State {
            object Group : State()
            object Action : State()
        }
    }

}

