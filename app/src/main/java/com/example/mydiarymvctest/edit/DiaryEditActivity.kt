package com.example.mydiarymvctest.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.widget.Toolbar
import com.example.mydiarymvctest.R
import com.example.mydiarymvctest.presenter.DiaryEditPresenter
import com.example.mydiarymvctest.utils.ActivityUtils

class DiaryEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_edit)

        val diaryId = intent.getStringExtra(DiaryEditFragment.DIARY_ID)
        initToolbar(diaryId)
        initFragment(diaryId)

    }

    private fun initToolbar(diaryId: String?) {
        // 从布局文件中加载顶部导航Toolbar
        val toolbar = findViewById<Toolbar>(R.id.edit_toolbar)
        // 自定义顶部导航Toolbar为ActionBar
        setSupportActionBar(toolbar)
        // 设置导航栏标题
        setToolbarTitle(TextUtils.isEmpty(diaryId))
    }

    private fun setToolbarTitle(isAdd: Boolean) {
        if (isAdd) { // 是否是写日记操作
            supportActionBar?.setTitle(R.string.add)
        } else { // 设置标题为修改日记
            supportActionBar?.title = getString(R.string.edit)
        }
    }

    private fun initFragment(diaryId: String?) {
        // 初始化Fragment
        var addEditFragment = getDiaryEditFragment()
        if (addEditFragment == null) {   // 查找是否已经创建过日记Fragment
            // 创建日记Fragment
            addEditFragment = initEditFragment(diaryId)
        }

        val diaryEditPresenter = DiaryEditPresenter(
            diaryId, // 日记ID
            addEditFragment // 修改日记的Fragment
        ) // 初始化Presenter
        // 将创建的Presenter传入Fragment
        addEditFragment.setPresenter(diaryEditPresenter)
    }

    private fun initEditFragment(diaryId: String?): DiaryEditFragment {
        val addEditFragment = DiaryEditFragment() // 创建修改日记Fragment

        if (intent.hasExtra(DiaryEditFragment.DIARY_ID)) {
            val bundle = Bundle()
            // 将日记唯一标识保存到日记Fragment的Arguments
            bundle.putString(DiaryEditFragment.DIARY_ID, diaryId)
            addEditFragment.arguments = bundle
        }

        // 将日记Fragment添加到Activity显示
        ActivityUtils.addFragmentToActivity(supportFragmentManager, addEditFragment, R.id.edit_content)
        return addEditFragment
    }

    private fun getDiaryEditFragment(): DiaryEditFragment? {
        // 通过FragmentManager查找日记展示Fragment
        return supportFragmentManager.findFragmentById(R.id.edit_content) as DiaryEditFragment?
    }
}