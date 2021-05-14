package com.example.mydiarymvctest.utils

import com.example.mydiarymvctest.model.Diary

/**
 * @author lewisli
 * @date 5/12
 * 集合工具类
 */
object  CollectionUtils {
    fun isEmpty(map: Map<String?, Diary?>?): Boolean {
        return map == null || map.isEmpty()
    }
}