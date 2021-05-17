package com.example.mydiarymvctest.edit

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import com.example.mydiarymvctest.R
import com.example.mydiarymvctest.presenter.DiaryEditContract

/**
 * @author lewisli
 * @date 5/14
 * 日记编辑页面
 */
class DiaryEditFragment : Fragment(), DiaryEditContract.View {

    companion object {
        // 日记ID
        const val DIARY_ID = "diary_id"
    }

    private lateinit var title: TextView
    private lateinit var description: TextView
    private var presenter: DiaryEditContract.Presenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_diary_edit, container, false)
        title = root.findViewById(R.id.edit_title) // 标题
        description = root.findViewById(R.id.edit_description) //详情
        setHasOptionsMenu(true)
        return root
    }

    override fun onResume() {
        super.onResume()
        presenter?.start()
    }

    override fun onDestroy() {
        presenter?.destroy()
        super.onDestroy()
    }

    // 加载菜单布局文件
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_done, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 获取按钮id
        when (item.itemId) {
            // 点击完成按钮
            R.id.menu_done -> {
                presenter?.saveDiary(title.text.toString(), description.text.toString()) // 保存日记信息
                return true
            }
        }
        return false
    }

    // 显示错误
    override fun showError() {
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    // 显示日记列表
    override fun showDiariesList() {
        // 标记处理成功
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }

    // 设置标题
    override fun setTitle(title: String) {
        this.title.text = title
    }

    // 设置详情
    override fun setDescription(description: String) {
        this.description.text = description
    }

    // 判断fragment是否已经添加到了Activity中
    override fun isActive(): Boolean {
        return isAdded
    }

    override fun setPresenter(presenter: DiaryEditContract.Presenter) {
        this.presenter = presenter
    }
}