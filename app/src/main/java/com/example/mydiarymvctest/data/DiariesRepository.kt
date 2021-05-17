package com.example.mydiarymvctest.data

import com.example.mydiarymvctest.model.DiariesLocalDataSource
import com.example.mydiarymvctest.model.Diary

/**
 * @author lewisli
 * @date 5/13
 * 日记数据仓库
 */
class DiariesRepository private constructor(): DataSource<Diary> {

    // region field

    private var localDataSource: DataSource<Diary> = DiariesLocalDataSource.get()

    private var memoryCache: MutableMap<String, Diary> = LinkedHashMap()

    // endregion

    // 获取所有日记
    override fun getAll(callback: DataCallback<List<Diary>>) {
        // 内存中有数据
        if (!memoryCache.isNullOrEmpty()) {
            callback.onSuccess(ArrayList(memoryCache.values))
            return
        }
        // 否则向本地中获取
        localDataSource.getAll(object : DataCallback<List<Diary>> {
            // 更新缓存
            override fun onSuccess(data: List<Diary>) {
                updateMemory(data)
                callback.onSuccess(ArrayList(memoryCache.values))
            }

            override fun onError() {
                callback.onError()
            }

        })
    }

    // 获取某个日记
    override fun get(id: String?, callback: DataCallback<Diary>) {
        val cacheDiary = getDiaryByIdFromMemory(id)
        // 内存中有数据
        if (cacheDiary != null) {
            callback.onSuccess(cacheDiary)
            return
        }
        localDataSource.get(id, object : DataCallback<Diary> {
            override fun onSuccess(data: Diary) {
                memoryCache[data.id] = data
                callback.onSuccess(data)
            }

            override fun onError() {
                callback.onError()
            }

        })
    }

    override fun update(diary: Diary) {
        // 更新日记数据
        localDataSource.update(diary)
        memoryCache[diary.id] = diary
    }

    override fun clear() {
        localDataSource.clear()
        memoryCache.clear()
    }

    override fun delete(id: String) {
        localDataSource.delete(id)
        memoryCache.remove(id)
    }

    companion object {
        private var INSTANCE: DiariesRepository? = null
            get() {
                if (field == null) {
                    field = DiariesRepository()
                }
                return field
            }
        @Synchronized
        fun get(): DiariesRepository {
            return INSTANCE!!
        }
    }

    // region private

    private fun updateMemory(diaryList: List<Diary>) {
        memoryCache.clear()
        for (diary in diaryList) {
            memoryCache[diary.id] = diary
        }
    }

    private fun getDiaryByIdFromMemory(id: String?): Diary? {
        return if (memoryCache.isNullOrEmpty()) {
            null
        } else {
            memoryCache[id]
        }
    }

    // endregion

}