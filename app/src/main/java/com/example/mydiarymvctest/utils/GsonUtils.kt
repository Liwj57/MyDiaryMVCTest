package com.example.mydiarymvctest.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

object GsonUtils {

    // region field
    private val GSON: Gson = createGson()

    // endregion

    // region public

    // 将对象转换为Json格式String
    fun toJson(obj: Any): String {
        return GSON.toJson(obj)
    }

    fun <T> fromJson(json: String?, type: Type?): T { // 将JSON格式String转换为对象
        return GSON.fromJson(json, type)
    }

    // endregion

    // region private

    // 创建GSON实例
    private fun createGson(): Gson {
        // 创建Gson实例
        val builder = GsonBuilder()
        builder.serializeNulls()
        return builder.create()
    }

    // endregion
}