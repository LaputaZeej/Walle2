package com.bugull.walle.action.content.basic.phone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bugull.android.util.HDSettings
import com.bugull.walle.R
import com.bugull.walle.WalleReal
import com.bugull.walle.action.base.BaseActionView
import com.bugull.walle.ext.dp2px

/**
 * Author by xpl, Date on 2021/5/18.
 */
class PhoneInfoActionView : BaseActionView() {
    override fun onCreateView(context: Context, root: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.action_phone_info, null, false)
    }

    override fun onViewCreated(root: ViewGroup) {
        super.onViewCreated(root)
        find<View>(R.id.iv_close)?.setOnClickListener {
            WalleReal.dismiss(this)
        }
        find<View>(R.id.iv_back)?.visibility = View.GONE
        find<View>(R.id.tv_more)?.setOnClickListener {
            HDSettings.gotoDeviceInfo(root.context)
        }
        find<RecyclerView>(R.id.recycler_phone_info)?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = PhoneAdapter(createPhoneInfo())
        }
    }

    private fun createPhoneInfo(): List<PhoneInfo> = mutableListOf<PhoneInfo>().apply {
        add(PhoneInfo("设备名称", "设备名称", "1213131"))
        add(PhoneInfo("型号", "型号", "141414141"))
        add(PhoneInfo("版本号", "版本号", "1414141"))
        add(PhoneInfo("Android版本", "Android版本", "1414141"))
        add(PhoneInfo("IMEI", "IMEI", "141414"))
        add(PhoneInfo("MEID", "MEID", "141414"))
        add(PhoneInfo("处理器", "处理器", "1414"))
        add(PhoneInfo("运行内存", "运行内存", "14141"))
        add(PhoneInfo("手机存储", "手机存储", "1414141"))
        add(PhoneInfo("屏幕", "屏幕", "屏幕"))
    }.toList()


    override fun initLayoutParams(params: ViewGroup.LayoutParams) {
        super.initLayoutParams(params)
        when (params) {
            is WindowManager.LayoutParams -> {
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = dp2px(HEIGHT)
                params.x = 0
                params.y = 0
            }
        }
    }

    companion object {
        private const val HEIGHT = 300f
    }
}