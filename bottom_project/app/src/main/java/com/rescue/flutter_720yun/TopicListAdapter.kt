package com.rescue.flutter_720yun

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rescue.flutter_720yun.models.homemodel.HomeListModel
import com.squareup.picasso.Picasso

class TopicListAdapter(private val list: List<HomeListModel>): RecyclerView.Adapter<TopicListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.nick_name)
        var imgView = view.findViewById<ImageView>(R.id.head_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.userInfo.username
        val imgStr = "http://img.rxswift.cn/${item.userInfo.avator}"
        Picasso.get().load(imgStr).into(holder.imgView);
    }
}