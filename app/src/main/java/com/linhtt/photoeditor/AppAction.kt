package com.linhtt.photoeditor

import com.google.gson.Gson

enum class AppAction(val value: String) {
    ACTION_ADD_STICKER("add_sticker");
    var data :String = ""
    fun setData(data:Any):AppAction{
        this.data =Gson().toJson(data)
        return this
    }
    fun <T> getData(target:Class<T> ):T{
        return Gson().fromJson(data,target)
    }
}