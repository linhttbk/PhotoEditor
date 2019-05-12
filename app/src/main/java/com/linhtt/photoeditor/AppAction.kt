package com.linhtt.photoeditor

import com.google.gson.Gson

enum class AppAction(val value: String) {
    ACTION_ADD_STICKER("add_sticker"),
    ACTION_ADD_TEXT_STICKER("add_text"),
    ACTION_CHANGE_FONT("font"),
    ACTION_CHANGE_COLOR("color"),
    ACTION_CHANGE_BACKGROUND("background"),
    ACTION_UPDATE("update"),
    ACTION_EDIT_TEXT_STICKER("edit_text");
    var data :String = ""
    fun setData(data:Any):AppAction{
        this.data =Gson().toJson(data)
        return this
    }
    fun <T> getData(target:Class<T> ):T{
        return Gson().fromJson(data,target)
    }
}