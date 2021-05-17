package com.example.mydiarymvctest.data

import androidx.annotation.NonNull

/**
 * @author lewisli
 * @date 5/11
 * 本地数据源
 */
interface DataSource<T> {
    // 获取所有数据T
    fun getAll(@NonNull callback: DataCallback<List<T>>)
    // 获取某个数据T
    fun get(id: String?, callback: DataCallback<T>)
    // 更新某个数据T
    fun update(diary: T)
    // 清除所有数据T
    fun clear()
    // 删除某个数据T
    fun delete(id: String)
}