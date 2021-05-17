package com.example.mydiarymvctest.base

// view的基类
interface BaseView<T> {
    // 传入presenter
    fun setPresenter(presenter: T)
}