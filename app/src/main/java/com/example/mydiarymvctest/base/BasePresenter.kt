package com.example.mydiarymvctest.base

interface BasePresenter {
    // presenter生命周期开始
    fun start()
    // presenter生命周期结束
    fun destroy()
}