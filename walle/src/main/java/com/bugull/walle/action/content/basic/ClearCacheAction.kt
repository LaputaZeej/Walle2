package com.bugull.walle.action.content.basic

import android.content.Context
import com.bugull.android.util.HDSettings
import com.bugull.walle.R
import com.bugull.walle.action.content.home.Action
import com.bugull.walle.action.content.home.Group

/**
 * Author by xpl, Date on 2021/5/18.
 */
class ClearCacheAction(
    override val group: Group = Group.Basic,
    override val name: Int = R.string.action_name_basic_clear_cache,
    override val icon: Int = R.drawable.ic_baseline_arrow_back_ios_24,
    override val step: Action.Step = Action.Step.NAN
) : Action {
    override fun doAction(context: Context) {
        HDSettings.gotoDevelopment(context)
    }

    override fun init(context: Context) {

    }
}