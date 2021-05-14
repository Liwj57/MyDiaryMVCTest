package com.example.mydiarymvctest.model

import android.icu.text.CaseMap
import java.util.*

/**
 * @author lewisli
 * @date 5/11
 * 日记Model
 */
class Diary @JvmOverloads constructor(title: String, description: String, id:String = UUID.randomUUID().toString()) {
    // 日记标识
    var id: String = "--"
    // 日记标题
    var title: String = "--"
    // 日记内容
    var description: String = "--"

    init {
        this.id = id
        this.title = title
        this.description = description
    }


}