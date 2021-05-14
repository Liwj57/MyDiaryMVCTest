package com.example.mydiarymvctest.data.mock

import com.example.mydiarymvctest.model.Diary

class MockDiaries {

    companion object {
        private const val description = "今天，天气晴朗，在第九巷大道上，我遇到一群年轻人，" +
                "优雅地演奏着手风琴，围观人群大都是一群少男少女，他们目不转睛"

        fun mock(): MutableMap<String, Diary> {
            return mock(LinkedHashMap())
        }

        fun mock(data: MutableMap<String, Diary>): MutableMap<String, Diary> {
            val test1 = getDiary("2021-01-01 元旦")
            data[test1.id] = test1

            val test2 = getDiary("2021-02-11 除夕")
            data[test2.id] = test2

            val test3 = getDiary("2021-02-12 春节")
            data[test3.id] = test3

            val test4 = getDiary("2021-02-26 元宵佳节")
            data[test4.id] = test4

            val test5 = getDiary("2021-03-08 妇女节")
            data[test5.id] = test5

            val test6 = getDiary("2021-04-04 清明节")
            data[test6.id] = test6

            val test7 = getDiary("2021-05-01 劳动节")
            data[test7.id] = test7

            val test8 = getDiary("2021-05-04 青年节")
            data[test8.id] = test8

            return data
        }

        private fun getDiary(title: String): Diary {
            return Diary(title, description)
        }
    }
}