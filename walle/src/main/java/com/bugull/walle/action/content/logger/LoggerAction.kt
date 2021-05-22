package com.bugull.walle.action.content.logger

import android.content.Context
import com.bugull.walle.R
import com.bugull.walle.WalleReal
import com.bugull.walle.action.content.home.Action
import com.bugull.walle.action.content.home.Group
import com.bugull.walle.action.core.ActionInfo

/**
 * Author by xpl, Date on 2021/5/18.
 */
class LoggerAction(
    override val group: Group = Group.Basic,
    override val name: Int = R.string.action_name_logger,
    override val icon: Int = R.drawable.ic_baseline_arrow_back_ios_24,
    override val step: Action.Step = Action.Step.Dev
) : Action {
    override fun doAction(context: Context) {
        WalleReal.show(ActionInfo(LoggerActionView::class.java))

    }

    override fun init(context: Context) {

    }
}