package com.example.mydiarymvctest.presenter

import android.text.TextUtils
import com.example.mydiarymvctest.data.DataCallback
import com.example.mydiarymvctest.data.DiariesRepository
import com.example.mydiarymvctest.model.Diary

class DiaryEditPresenter(
    private val diaryId: String?,
    private val mView: DiaryEditContract.View
    ): DiaryEditContract.Presenter {

    private val diariesRepository = DiariesRepository.get()

    override fun saveDiary(title: String, description: String) {
        if (isAddDiary()) {
            createDiary(title, description)
        } else {
            updateDiary(title, description)
        }
    }

    override fun requestDiary() {
        if (isAddDiary()) {
            return
        }
        diariesRepository.get(diaryId, object: DataCallback<Diary> {
            override fun onSuccess(data: Diary) {
                if (!mView.isActive()) {
                    return
                }
                mView.setTitle(data.title)
                mView.setDescription((data.description))
            }

            override fun onError() {
                if (!mView.isActive()) {
                    return
                }
                mView.showError()
            }

        })
    }

    override fun start() {
        requestDiary() // 获取日记信息
    }

    override fun destroy() {

    }

    private fun isAddDiary(): Boolean {
        return TextUtils.isEmpty(diaryId)
    }

    private fun createDiary(title: String, description: String) {
        val newDiary = Diary(title, description)
        diariesRepository.update(newDiary)
        mView.showDiariesList()
    }

    private fun updateDiary(title: String, description: String) {
        val diary = Diary(title, description, diaryId) // 创建指定id的日记对象
        diariesRepository.update(diary)
        mView.showDiariesList()
    }

}