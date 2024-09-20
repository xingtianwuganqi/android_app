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
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.setMargins
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rescue.flutter_720yun.models.homemodel.TagInfoModel

//class TopicListAdapter(private val context: Context, private val list: List<HomeListModel>): RecyclerView.Adapter<TopicListAdapter.ViewHolder>() {
//    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
//        val name = view.findViewById<TextView>(R.id.nick_name)
//        var imgView = view.findViewById<ImageView>(R.id.head_img)
//        val content = view.findViewById<TextView>(R.id.content)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.home_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//       return list.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = list[position]
//        holder.name.text = item.userInfo.username
//        holder.name.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
//        val imgStr = "http://img.rxswift.cn/${item.userInfo.avator}"
//        Glide.with(context)
//            .load(imgStr)
//            .placeholder(R.drawable.icon_eee)
//            .into(holder.imgView)
//        holder.imgView.scaleType = ImageView.ScaleType.CENTER_CROP
//        holder.content.text = item.content
//    }
//}

class HomeListAdapter(private val context: Context): PagingDataAdapter<HomeListModel, HomeListViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        return HomeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(context, item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HomeListModel>() {
        override fun areItemsTheSame(oldItem: HomeListModel, newItem: HomeListModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeListModel, newItem: HomeListModel): Boolean {
            return oldItem == newItem
        }
    }

}


class HomeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById<TextView>(R.id.nick_name)
    private val imgView: ImageView = view.findViewById<ImageView>(R.id.head_img)
    private val content: TextView = view.findViewById<TextView>(R.id.content)
    private val tag_info: RecyclerView = view.findViewById(R.id.tag_info)

    fun bind(context: Context, item: HomeListModel?) {
        name.text = item?.userInfo?.username
        name.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        item?.userInfo?.avator.let {
            val imgStr = "http://img.rxswift.cn/${it}"
            Glide.with(context)
                .load(imgStr)
                .placeholder(R.drawable.icon_eee)
                .into(imgView)
        }

        imgView.scaleType = ImageView.ScaleType.CENTER_CROP
        content.text = item?.content

        if (item?.tagInfos?.isNotEmpty() == true) {
            tag_info.visibility = View.VISIBLE
            tag_info.adapter = item?.tagInfos?.let { TagInfoAdapter(it) }
            tag_info.layoutManager = LinearLayoutManager(context)
            val paddingTop = 26 * context.resources.displayMetrics.density
            content.setPadding(0, paddingTop.toInt(), 0, 0)
        }else{
            tag_info.visibility = View.GONE
            content.setPadding(0, 0, 0, 0)
        }

    }
}

class HomeLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<HomeLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: HomeLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HomeLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.load_state_view, parent, false)
        return HomeLoadStateViewHolder(view, retry)
    }
}

class HomeLoadStateViewHolder(view: View, retry: () ->Unit): RecyclerView.ViewHolder(view) {
    val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    val errorMessage = view.findViewById<TextView>(R.id.errorMessage)
    val retryButton = view.findViewById<Button>(R.id.retryButton)

    fun bind(loadState: LoadState) {
        // 控制 ProgressBar 的可见性
        when (loadState) {
            is LoadState.Loading -> {
                // 显示加载中的 ProgressBar
                progressBar.visibility = View.VISIBLE
                errorMessage.visibility = View.GONE
                retryButton.visibility = View.GONE
            }
            is LoadState.Error -> {
                // 显示错误信息和重试按钮，隐藏 ProgressBar
                progressBar.visibility = View.GONE
                errorMessage.visibility = View.VISIBLE
                retryButton.visibility = View.VISIBLE
                // 显示具体的错误信息
                errorMessage.text = loadState.error.localizedMessage
            }
            is LoadState.NotLoading -> {
                // 隐藏所有状态视图
                progressBar.visibility = View.GONE
                errorMessage.visibility = View.GONE
                retryButton.visibility = View.GONE
            }
        }
    }
}


class TagInfoAdapter(private val tagList: List<TagInfoModel>): RecyclerView.Adapter<TagInfoAdapter.ViewHandler>() {

    inner class ViewHandler(view: View): RecyclerView.ViewHolder(view) {
        val tagText: TextView = view.findViewById<TextView>(R.id.tag_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHandler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tag_item, parent, false)
        return ViewHandler(view)
    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    override fun onBindViewHolder(holder: ViewHandler, position: Int) {
        val value = tagList[position]
        holder.tagText.text = value.tag_name
        val background = GradientDrawable()
// 设置背景颜色
        background.setColor(Color.parseColor("#EEEEEE"))
        // 设置圆角半径（dp转px）
        val cornerRadius: Float = 5F
        background.cornerRadius = cornerRadius
// 将这个背景应用到TextView
        holder.tagText.background = background
    }
}