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
import android.widget.Button
import android.widget.ProgressBar
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

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