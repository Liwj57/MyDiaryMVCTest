package com.example.mydiarymvctest.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiarymvctest.R
import com.example.mydiarymvctest.controller.DiariesController

class DiariesFragment : Fragment() {

    // region field

    private var diaryController: DiariesController? = null

    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 创建日记控制器
        diaryController = DiariesController(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 加载日记布局界面
        val root: View = inflater.inflate(R.layout.fragment_diaries, container, false)
        // 将日记列表控件传入控制器
        diaryController?.setDiariesList(root.findViewById(R.id.diaries_list) as RecyclerView)
        return root
    }

    override fun onResume() {
        super.onResume()
        // 加载日志数据
        diaryController?.loadDiaries()
    }

    // 创建菜单，重写父类中的方法
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // 加载菜单的布局文件
        inflater.inflate(R.menu.menu_write, menu)
    }

    // 菜单选择监听，重写父类中的方法
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 判断点击事件
        when (item.itemId) {
            // 点击添加按钮,通知控制器添加新的日记
            R.id.menu_write -> {
                diaryController?.gotoWriteDiary()
                return true
            }
        }
        return false
    }
}