//package com.bugull.walle.action.content.basic.phone
//
//import android.view.View
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bugull.walle.R
//import com.bugull.walle.action.base.ListAdapter
//import com.bugull.walle.action.base.ListItem
//
///**
// * Author by xpl, Date on 2021/5/18.
// */
//class PhoneInfoAdapter(list: List<ListItem<PhoneInfo>>) : ListAdapter<PhoneInfo>(list) {
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when(holder){
//            is ListViewHolder.ItemViewHolder->{
//                val phoneInfo = list[position].data
//                holder.itemView.apply {
//                    findViewById<TextView>(R.id.tv_title).text = phoneInfo.title
//                    findViewById<TextView>(R.id.tv_value).apply {
//                        if (phoneInfo.value.isEmpty()) {
//                            visibility = View.GONE
//                        } else {
//                            visibility = View.VISIBLE
//                            text = phoneInfo.value
//                        }
//                    }
//                    findViewById<TextView>(R.id.tv_desc).apply {
//                        if (phoneInfo.desc.isEmpty()) {
//                            visibility = View.GONE
//                        } else {
//                            visibility = View.VISIBLE
//                            text = phoneInfo.desc
//                        }
//                    }
//                }
//            }
//
//            is ListViewHolder.ClickViewHolder->{
//                val phoneInfo = list[position].data
//                holder.itemView.apply {
//                    findViewById<TextView>(R.id.tv_title).text = phoneInfo.title
//                    setOnClickListener {
//
//                    }
//                }
//            }
//
//            is ListViewHolder.BottomViewHolder->{
//
//            }
//        }
//    }
//}