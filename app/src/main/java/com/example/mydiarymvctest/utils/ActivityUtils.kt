package com.example.mydiarymvctest.utils

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

object ActivityUtils {
    fun addFragmentToActivity(@NonNull fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {
        // Fragment 事务处理
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        // 添加Fragment,frameId作为Fragment的id
        transaction.add(frameId, fragment)
        // 提交事务
        transaction.commit()
    }
}