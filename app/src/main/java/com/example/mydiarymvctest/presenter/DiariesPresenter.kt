package com.example.mydiarymvctest.presenter

import android.app.Activity
import android.view.View
import com.example.mydiarymvctest.adapter.DiariesAdapter
import com.example.mydiarymvctest.data.DataCallback
import com.example.mydiarymvctest.data.DiariesRepository
import com.example.mydiarymvctest.model.Diary
import java.util.ArrayList

class DiariesPresenter(private val mView: DiariesContract.View): DiariesContract.Presenter {

    private val diariesRepository: DiariesRepository = DiariesRepository.get()
    private var diariesAdapter: DiariesAdapter? = null

    override fun loadDiaries() {
        // 通过数据仓库获取数据
        diariesRepository.getAll(object : DataCallback<List<Diary>> {
            override fun onSuccess(data: List<Diary>) {
                if (!mView.isActive()) {
                    return
                }
                updateDiaries(data)
            }

            override fun onError() {
                if (!mView.isActive()) {
                    return
                }
                mView.showError()
            }

        })
    }

    override fun addDiary() {
        mView.gotoWriteDiary()
    }

    override fun updateDiary(diary: Diary) {
        mView.gotoUpdateDiary(diary.id)
    }

    override fun onResult(requestCode: Int, resultCode: Int) {
        if (Activity.RESULT_OK != resultCode) {
            return
        }
        mView.showSuccess()
    }

    override fun start() {
        initAdapter()
        loadDiaries()
    }

    override fun destroy() {

    }

    // 初始化适配器
    private fun initAdapter() {
        // 创建日记列表的适配器
        diariesAdapter = DiariesAdapter(ArrayList<Diary>())
        // 设置列表的条目长按事件
        diariesAdapter?.setLongClickListener(object : DiariesAdapter.OnLongClickListener<Diary> {
            override fun onLongClick(v: View, data: Diary): Boolean {
                updateDiary(data) // 更新日记
                return false
            }
        })
        mView.setListAdapter(diariesAdapter)
    }

    private fun updateDiaries(diaries: List<Diary>) {
        diariesAdapter?.update(diaries)
    }


}