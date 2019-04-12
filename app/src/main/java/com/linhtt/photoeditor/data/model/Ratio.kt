package com.linhtt.photoeditor.data.model

data class Ratio(val title: Int, val icon: Int, val activeIcon: Int, val type: RatioType) {
    var isSelected: Boolean = false

    enum class RatioType {
        FREE, RATIO1_1, RATIO1_2, RATIO4_5, RATIO5_4, RATIO3_4, RATIO4_3, RATIO3_2, RATIO2_3, RATIO9_16, RATIO16_9
    }
}