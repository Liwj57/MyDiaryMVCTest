package com.example.mydiarymvctest.model

import com.example.mydiarymvctest.data.DataCallback
import com.example.mydiarymvctest.data.DataSource
import com.example.mydiarymvctest.data.mock.MockDiaries
import com.example.mydiarymvctest.utils.GsonUtils
import com.example.mydiarymvctest.utils.SharedPreferencesUtils
import com.google.gson.reflect.TypeToken

class DiariesLocalDataSource private constructor(): DataSource<Diary> {

    // region field

    private var spUtils: SharedPreferencesUtils? = null

    // endregion

    init {
        // 获取SharedPreferences,以管理本地缓存信息
        spUtils = SharedPreferencesUtils.get(DIARY_DATA)
        // 获取本地日记信息
        val diaryStr = spUtils?.getData(All_DIARY)
        // 解析本地日记信息
        val diariesObj: LinkedHashMap<String, Diary> = json2Obj(diaryStr!!)
        localData = if (!diariesObj.isNullOrEmpty()) {
            diariesObj
        } else {
            // 构造测试数据
            MockDiaries.mock()
        }
    }

    // region override

    override fun getAll(callback: DataCallback<List<Diary>>) {
        if (localData.isEmpty()) {
            // 内存缓存为空，通知查询错误
            callback.onError()
        } else {
            // 通知查询成功
            callback.onSuccess(ArrayList(localData.values))
        }
    }

    override fun get(id: String?, callback: DataCallback<Diary>) {
        val diary: Diary? = localData[id]
        if (diary != null) {
            callback.onSuccess(diary)
        } else {
            callback.onError()
        }
    }

    override fun update(diary: Diary) {
        localData[diary.id] = diary
        spUtils?.putData(All_DIARY, obj2Json())
    }

    override fun clear() {
        localData.clear()
        spUtils?.removeData(All_DIARY)
    }

    override fun delete(id: String) {
        localData.remove(id)
        spUtils?.putData(All_DIARY, obj2Json())
    }

    // endregion

    companion object {
        const val DIARY_DATA = "diary_date"
        const val All_DIARY = "all_diary"
        // 本地数据内存缓存
        private var localData: MutableMap<String, Diary> = LinkedHashMap()

        @Volatile
        private var INSTANCE:DiariesLocalDataSource? = null
            get() {
                if (field == null) {
                    field = DiariesLocalDataSource()
                }
                return field
            }

        @Synchronized
        fun get(): DiariesLocalDataSource {
            return INSTANCE!!
        }
    }

    // region private

    private fun obj2Json(): String {
        return GsonUtils.toJson(localData)
    }

    private fun json2Obj(diaryStr: String): LinkedHashMap<String, Diary> {
        // 将日记json数据转换为日记对象
        return GsonUtils.fromJson(diaryStr, object : TypeToken<java.util.LinkedHashMap<String, Diary>>() {}.type)
            ?: LinkedHashMap()
    }

    // endregion

}