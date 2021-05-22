package com.bugull.walle.action.content.basic.app

import android.content.Context
import com.bugull.android.util.HDSettings
import com.bugull.walle.R
import com.bugull.walle.action.content.home.Action
import com.bugull.walle.action.content.home.Group

/**
 * Author by xpl, Date on 2021/5/18.
 */
class AppInfoAction(
    override val group: Group = Group.Basic,
    override val name: Int = R.string.action_name_app,
    override val icon: Int = R.drawable.ic_baseline_arrow_back_ios_24,
    override val step: Action.Step = Action.Step.Dev
) : Action {
    override fun doAction(context: Context) {
        HDSettings.gotoAppInfo(context)
    }

    override fun init(context: Context) {

    }
}