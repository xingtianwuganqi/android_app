package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerActivity : AppCompatActivity() {

    var fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        initFruits()
//        val layoutManager = LinearLayoutManager(this)
        val layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitRecycleAdapter(fruitList)
        recyclerView.adapter = adapter

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

class FruitRecycleAdapter(val fruitList: List<Fruit>): RecyclerView.Adapter<FruitRecycleAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.itemImage)
        val fruitText: TextView = view.findViewById(R.id.itemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fruit_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitText.text = fruit.name
        holder.fruitImage.setImageResource(R.drawable.applogo)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }
}