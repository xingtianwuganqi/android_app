package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class TabbarActivity : AppCompatActivity() {


    //定义fragment
    private var homeFragment: SecondFragment?=null
    private var fabuFragment: SecondFragment?=null
    private var fuwuFragment: SecondFragment?=null
    private var newsFragment: SecondFragment?=null
    private var userFragment: SecondFragment?=null
    //用于避免fragment重合到一起
    private var ShowFragment: Fragment?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbar)

        val tab_group = findViewById<MaterialButtonToggleGroup>(R.id.tab_group)
        tab_group.addOnButtonCheckedListener { group, checkedId, isChecked ->
            //拿到制作了按钮的数量
            val childCount = group.childCount
            //index用于后面对fragment操作
            var index = 0
            for (i in 0 until childCount) {
                val childAt = group.getChildAt(i) as MaterialButton
                //让被选中的按钮改变颜色
                if (childAt.id == checkedId) {
                    index = i
                    childAt.setTextColor(Color.GREEN)
                    childAt.iconTint = ColorStateList.valueOf(Color.GREEN)
                } else {
                    childAt.setTextColor(Color.WHITE)
                    childAt.iconTint = ColorStateList.valueOf(Color.WHITE)
                }
            }
            switchFragement(index)
        }
        //使其默认指向第一个按钮
        tab_group.check(R.id.tab_1)
    }

    //将按钮与fragment绑定
    private fun switchFragement(index: Int) {
        //让返回值变为fragment
        val fragment = when(index){
            0->{
                if (homeFragment == null){
                    homeFragment = SecondFragment()
                }
                homeFragment
            }
            1 ->{
                if (newsFragment == null){
                    newsFragment = SecondFragment()
                }
                newsFragment
            }
            2->{
                if (fuwuFragment == null){
                    fuwuFragment = SecondFragment()
                }
                fuwuFragment
            }
            3->{
                if (fabuFragment == null){
                    fabuFragment = SecondFragment()
                }
                fabuFragment
            }
            4->{
                if (userFragment == null){
                    userFragment = SecondFragment()
                }
                userFragment
            }
            else -> {
                return
            }
        }?:return
        //开启fragment事务管理
        val ft = supportFragmentManager.beginTransaction()
        //判断返回的fragment中是否被绑定
        if (!fragment.isAdded){
            ft.add(R.id.container,fragment)
        }
        //显示被选中的fragment
        ft.show(fragment)
        //避免所有fragment重合
        if (ShowFragment!=null){
            ft.hide(ShowFragment!!)
        }
        ShowFragment = fragment
        //提交事务
        ft.commitAllowingStateLoss()

    }

}