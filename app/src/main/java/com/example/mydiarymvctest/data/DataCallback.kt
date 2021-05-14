package com.example.mydiarymvctest.data

interface DataCallback<T> {
    fun onSuccess(data: T) // 通知成功

    fun onError()          // 通知失败
}