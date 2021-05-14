package com.example.mydiarymvctest.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import androidx.collection.SimpleArrayMap
import com.example.mydiarymvctest.EnApplication

class SharedPreferencesUtils private constructor(spName: String, mode: Int) {
    private val mSharedPreferences: SharedPreferences? = EnApplication.get().getSharedPreferences(spName, mode)

    fun putData(@NonNull key: String, value: String) {
        mSharedPreferences!!.edit()
            .putString(key, value)
            .apply() // 将数据存入SharedPreferences
    }

    fun getData(@NonNull key: String): String? {
        return mSharedPreferences?.getString(key, "") // 从SharedPreferences中获取数据
    }

    fun removeData(@NonNull key: String) {
        mSharedPreferences!!.edit()
            .remove(key)
            .apply() // 从SharedPreferences中删除数据
    }

    companion object {
        // SharedPreferences工具类内存缓存，以SharedPreferences的name为键
        private val mCaches: SimpleArrayMap<String, SharedPreferencesUtils> = SimpleArrayMap()
        fun get(spName: String): SharedPreferencesUtils {
            var spUtils = mCaches[spName] // 从内存缓存中获得SharedPreferences工具类
            if (spUtils == null) {
                spUtils = SharedPreferencesUtils(spName, Context.MODE_PRIVATE) // 创建SharedPreferences工具类
                mCaches.put(spName, spUtils) // 将SharedPreferences工具类存入内存缓存
            }
            return spUtils
        }
    }
}