package com.example.core



interface BaseConstant {
    fun empty() = ""
    val bus get() = GlobalBus.getBus()

}