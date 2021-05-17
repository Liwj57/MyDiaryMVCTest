package com.example.mydiarymvctest

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mydiarymvctest.fragment.DiariesFragment
import com.example.mydiarymvctest.presenter.DiariesPresenter
import com.example.mydiarymvctest.utils.ActivityUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 初始化顶部栏
        initToolbar()
        // 初始化fragment
        initFragment()
    }

    // region private

    private fun initToolbar() {

        val toolbar: Toolbar = findViewById(R.id.toolbar) // 从布局文件中加载顶部导航Toolbar

        setSupportActionBar(toolbar) // 自定义顶部导航Toolbar为ActionBar

    }

    private fun initFragment() {
        //初始化Fragment
        var diariesFragment: DiariesFragment? = getDiariesFragment()
        // 查找是否已经创建过日记Fragment
        if (diariesFragment == null) {
            // 创建日记Fragment
            diariesFragment = DiariesFragment()
            // 将日记Fragment添加到Activity显示
            ActivityUtils.addFragmentToActivity(supportFragmentManager, diariesFragment, R.id.content)
        }

        diariesFragment.setPresenter(DiariesPresenter(diariesFragment))
    }

    private fun getDiariesFragment(): DiariesFragment? {
        // 通过FragmentManager查找日记展示的Fragment
        return supportFragmentManager.findFragmentById(R.id.content) as DiariesFragment?
    }

    // endregion
}