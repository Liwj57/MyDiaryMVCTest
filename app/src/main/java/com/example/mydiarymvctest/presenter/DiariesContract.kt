package com.example.mydiarymvctest.presenter

import com.example.mydiarymvctest.adapter.DiariesAdapter
import com.example.mydiarymvctest.base.BasePresenter
import com.example.mydiarymvctest.base.BaseView
import com.example.mydiarymvctest.model.Diary

/**
 * @author lewisli
 * @date 5/14
 * 日记契约类
 */
interface DiariesContract {
    // 日记列表视图
    interface View : BaseView<Presenter> {

        fun gotoWriteDiary() // 跳转添加日记

        fun gotoUpdateDiary(diaryId: String) //跳转更新日记

        fun showSuccess() // 弹出成功提示

        fun showError() // 弹出失败提示

        fun isActive(): Boolean // 判断Fragment是否已经添加到了Activity中

        fun setListAdapter(listAdapter: DiariesAdapter?) // 设置适配器
    }

    interface Presenter: BasePresenter {

        fun loadDiaries() // 加载日记数据

        fun addDiary() // 跳转添加日记

        fun updateDiary(diary: Diary) // 跳转更新日记

        fun onResult(requestCode: Int, resultCode: Int) // 返回界面获取结果信息
    }
}