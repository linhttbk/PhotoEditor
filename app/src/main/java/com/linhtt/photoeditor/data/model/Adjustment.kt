package com.linhtt.photoeditor.data.model

data class Adjustment(val icon: Int, val activeIcon :Int,val title: Int, var percent: Int = 50){
    var isSelected: Boolean = false
}