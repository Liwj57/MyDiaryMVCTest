package com.example.mydiarymvctest.controller

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiarymvctest.EnApplication
import com.example.mydiarymvctest.R
import com.example.mydiarymvctest.adapter.DiariesAdapter
import com.example.mydiarymvctest.data.DataCallback
import com.example.mydiarymvctest.data.DiariesRepository
import com.example.mydiarymvctest.fragment.DiariesFragment
import com.example.mydiarymvctest.model.Diary
import java.util.ArrayList

class DiariesController(private val fragment: DiariesFragment) {

    private val diaryRepository = DiariesRepository.get()
    private lateinit var diaryAdapter: DiariesAdapter

    // region public

    fun loadDiaries() {
        diaryRepository.getAll(object : DataCallback<List<Diary>> {
            override fun onSuccess(data: List<Diary>) {
                // 数据获取成功
                processDiaries(data)
            }

            override fun onError() {
                // 数据获取失败，弹出错误提示
                showError()
            }

        })
    }

    fun gotoWriteDiary() {
        // 弹出功能未开放提示
        showMessage(fragment.getString(R.string.developing))
    }

    // 配置日记列表
    fun setDiariesList(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(fragment.context)
        recyclerView.adapter = diaryAdapter
        // 为列表条目添加分割线
        recyclerView.addItemDecoration(DividerItemDecoration(fragment.context, DividerItemDecoration.VERTICAL))
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    // endregion

    // region private

    private fun initAdapter() {
        // 创建日记列表的适配器
        diaryAdapter = DiariesAdapter(ArrayList<Diary>())
        // 设置列表的条目长按事件
        diaryAdapter.setLongClickListener(object : DiariesAdapter.OnLongClickListener<Diary> {
            override fun onLongClick(v: View, data: Diary): Boolean {
                showInputDialog(data)
                return false
            }

        })
    }

    // 弹出错误信息
    private fun showError() {
        showMessage(fragment.getString(R.string.error))
    }

    // 弹出文字提示信息
    private fun showMessage(message: String) {
        Toast.makeText(fragment.context, message, Toast.LENGTH_SHORT).show()
    }

    // 处理数据
    private fun processDiaries(diaries: List<Diary>) {
        // 更新列表中的日记数据
        diaryAdapter.update(diaries)
    }

    // 弹出输入对话框
    private fun showInputDialog(data: Diary) {
        // 创建输入框
        val editText = EditText(fragment.context)
        editText.setText(data.description)

        if (fragment.context == null) return
        AlertDialog.Builder(fragment.context!!)
            .setTitle(data.title)
            .setView(editText) // 确认按钮
            .setPositiveButton(EnApplication.get().getString(R.string.ok)) { dialog, which ->
                // 确认按钮点击监听，修改日记信息为输入框信息
                data.description = editText.text.toString()
                // 更新日记数据
                diaryRepository.update(data)
                // 重新加载列表
                loadDiaries()
            }
            .setNegativeButton(EnApplication.get().getString(R.string.cancel), null).show()
    }

    // endregion

    init {
        fragment.setHasOptionsMenu(true)
        initAdapter()
    }
}