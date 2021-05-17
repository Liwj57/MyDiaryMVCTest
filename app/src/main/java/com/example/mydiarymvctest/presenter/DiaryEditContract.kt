package com.example.mydiarymvctest.presenter

import com.example.mydiarymvctest.base.BasePresenter
import com.example.mydiarymvctest.base.BaseView

/**
 * @author lewisli
 * @date 5/14
 * 修改日记的契约接口
 */
interface DiaryEditContract {

    // 编辑日记的视图
    interface View: BaseView<Presenter> {

        fun showError() // 弹出错误提示

        fun showDiariesList() // 显示日记列表

        fun setTitle(title: String) // 设置标题

        fun setDescription(description: String) // 设置详情

        fun isActive(): Boolean // 判断Fragment是否已经添加到了Activity中
    }

    interface Presenter: BasePresenter {
        // 保存日记信息
        fun saveDiary(title: String, description: String)
        // 获取日记信息
        fun requestDiary()
    }
}