package com.example.mydiarymvctest.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiarymvctest.R
import com.example.mydiarymvctest.adapter.DiariesAdapter
import com.example.mydiarymvctest.base.BasePresenter
import com.example.mydiarymvctest.edit.DiaryEditActivity
import com.example.mydiarymvctest.edit.DiaryEditFragment
import com.example.mydiarymvctest.presenter.DiariesContract

// 日记展示页面
class DiariesFragment : Fragment(), DiariesContract.View {

    // region field

    private var mPresenter: DiariesContract.Presenter? = null
    private var recyclerView: RecyclerView? = null

    // endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 加载日记布局界面
        val root: View = inflater.inflate(R.layout.fragment_diaries, container, false)
        recyclerView = root.findViewById(R.id.diaries_list)
        //初始化adapter
        initDiariesList()

        setHasOptionsMenu(true) //设置界面有菜单功能

        return root
    }

    // fragment的生命周期onResume
    override fun onResume() {
        super.onResume()
        mPresenter?.start()
    }

    // fragment的生命周期onDestroy
    override fun onDestroy() {
        mPresenter?.destroy()
        super.onDestroy()
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
                mPresenter?.addDiary()
                return true
            }
        }
        return false
    }

    // 返回界面获取结果信息
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter?.onResult(requestCode, resultCode)
    }

    override fun gotoWriteDiary() {
        // 构造跳转页面的intent
        val intent = Intent(context, DiaryEditActivity::class.java)
        startActivity(intent)
    }

    // 跳转更新日记
    override fun gotoUpdateDiary(diaryId: String) {
        // 构造跳转页面的intent
        val intent = Intent(context, DiaryEditActivity::class.java)
        // 设置跳转携带信息
        intent.putExtra(DiaryEditFragment.DIARY_ID, diaryId)
        // 根据intent跳转
        context?.startActivity(intent)
    }

    // 弹出成功提示
    override fun showSuccess() {
        showMessage(getString(R.string.success))
    }

    // 弹出失败提示
    override fun showError() {
        showMessage(getString(R.string.error))
    }

    // 判断Fragment是否已经添加到了Activity中
    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setListAdapter(listAdapter: DiariesAdapter?) {
        recyclerView?.adapter = listAdapter
    }

    override fun setPresenter(presenter: DiariesContract.Presenter) {
        this.mPresenter = presenter
    }

    // 配置日记列表
    private fun initDiariesList() {
        recyclerView?.let {
            it.layoutManager = LinearLayoutManager(context)
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            it.itemAnimator = DefaultItemAnimator()
        }
    }

    // 展示文字弹出提示
    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}