package com.linhtt.photoeditor.data.model

data class Photo(var folder: String, var path: ArrayList<String>) {
    val thumb = if (!path.isEmpty()) path[0] else ""
}