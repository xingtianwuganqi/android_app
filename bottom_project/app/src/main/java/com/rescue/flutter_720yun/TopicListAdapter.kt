package com.rescue.flutter_720yun


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rescue.flutter_720yun.models.homemodel.HomeListModel
import android.content.Context
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class TopicListAdapter(private val context: Context, private val list: List<HomeListModel>): RecyclerView.Adapter<TopicListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.nick_name)
        var imgView = view.findViewById<ImageView>(R.id.head_img)
        val content = view.findViewById<TextView>(R.id.content)
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
        holder.name.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        val imgStr = "http://img.rxswift.cn/${item.userInfo.avator}"
        Glide.with(context)
            .load(imgStr)
            .placeholder(R.drawable.icon_eee)
            .into(holder.imgView)
        holder.imgView.scaleType = ImageView.ScaleType.CENTER_CROP
        holder.content.text = item.content
    }
}