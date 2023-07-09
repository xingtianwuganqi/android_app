package com.example.myapplication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textservice.TextInfo
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ListDetailActivity : AppCompatActivity() {

    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)
        initFruits()
        val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList)
        var listView: ListView = findViewById(R.id.listDetail)
        listView.adapter = adapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val fruit = fruitList[position]
            Toast.makeText(this,fruit.name,Toast.LENGTH_SHORT).show()
        }
    }

    private fun initFruits() {
        repeat(2) {
            fruitList.add(Fruit("Apple", "R.drawable.apple_pic"))
            fruitList.add(Fruit("Banana", "R.drawable.banana_pic"))
            fruitList.add(Fruit("Orange", "R.drawable.orange_pic"))
            fruitList.add(Fruit("Watermelon", "R.drawable.watermelon_pic"))
            fruitList.add(Fruit("Pear", "R.drawable.pear_pic"))
            fruitList.add(Fruit("Grape", "R.drawable.grape_pic"))
            fruitList.add(Fruit("Pineapple", "R.drawable.pineapple_pic"))
            fruitList.add(Fruit("Strawberry", "R.drawable.strawberry_pic"))
            fruitList.add(Fruit("go", "R.drawable.cherry_pic"))
            fruitList.add(Fruit("python", "R.drawable.mango_pic"))
        }
    }
}

class Fruit(val name: String, val imageId: String) {

}

class FruitAdapter(activity: Activity, val resourceId: Int, data: List<Fruit>): ArrayAdapter<Fruit>(activity, resourceId,data) {

    //内部类
    inner class ViewHolder(val fruitImage: ImageView, val fruitName: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        var viewHolder: ViewHolder
        if (convertView != null) {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }else{
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            val fruitText: TextView = view.findViewById(R.id.itemText)
            val fruitImage: ImageView = view.findViewById(R.id.itemImage)
            viewHolder = ViewHolder(fruitImage, fruitText)
            view.tag = viewHolder
        }

        val fruit = getItem(position)
        if (fruit != null) {
            viewHolder.fruitName.text = fruit.name
            viewHolder.fruitImage.setImageResource(R.drawable.applogo)
        }
        return view
    }

}

