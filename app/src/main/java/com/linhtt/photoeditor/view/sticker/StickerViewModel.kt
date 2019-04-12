package com.linhtt.photoeditor.view.sticker

import com.example.core.BaseViewModel
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.adapter.StickerAdapter
import com.linhtt.photoeditor.data.model.StickerData

class StickerViewModel(position: Int) : BaseViewModel() {
    val adapter = StickerAdapter(getStickersByPage(position))






    private fun getStickersByPage(page: Int): ArrayList<StickerData> {
        when (page) {
            0 -> return getAllEmojiPeople()
            1 -> return getAllEmojiEmotion()
            2 -> return getAllEmojiCelebration()
            3 -> return getAllEmojiNature()
            4 -> return getAllEmojiActivity()
            5 -> return getAllEmojiFood()
        }
        return getAllEmojiPeople()

    }

    private fun getAllEmojiPeople(): ArrayList<StickerData> {
        val result = ArrayList<StickerData>()
        result.add(StickerData(R.drawable.people_emoji_000))
        result.add(StickerData(R.drawable.people_emoji_001))
        result.add(StickerData(R.drawable.people_emoji_002))
        result.add(StickerData(R.drawable.people_emoji_003))
        result.add(StickerData(R.drawable.people_emoji_004))
        result.add(StickerData(R.drawable.people_emoji_005))
        result.add(StickerData(R.drawable.people_emoji_006))
        result.add(StickerData(R.drawable.people_emoji_007))
        result.add(StickerData(R.drawable.people_emoji_008))
        result.add(StickerData(R.drawable.people_emoji_009))
        result.add(StickerData(R.drawable.people_emoji_010))
        result.add(StickerData(R.drawable.people_emoji_011))
        result.add(StickerData(R.drawable.people_emoji_012))
        result.add(StickerData(R.drawable.people_emoji_013))
        result.add(StickerData(R.drawable.people_emoji_014))
        result.add(StickerData(R.drawable.people_emoji_015))
        result.add(StickerData(R.drawable.people_emoji_016))
        result.add(StickerData(R.drawable.people_emoji_017))
        result.add(StickerData(R.drawable.people_emoji_018))
        result.add(StickerData(R.drawable.people_emoji_019))
        result.add(StickerData(R.drawable.people_emoji_020))
        result.add(StickerData(R.drawable.people_emoji_021))
        result.add(StickerData(R.drawable.people_emoji_022))
        result.add(StickerData(R.drawable.people_emoji_023))
        result.add(StickerData(R.drawable.people_emoji_024))
        result.add(StickerData(R.drawable.people_emoji_025))
        result.add(StickerData(R.drawable.people_emoji_026))
        result.add(StickerData(R.drawable.people_emoji_027))
        result.add(StickerData(R.drawable.people_emoji_028))
        result.add(StickerData(R.drawable.people_emoji_029))
        result.add(StickerData(R.drawable.people_emoji_030))
        result.add(StickerData(R.drawable.people_emoji_031))
        result.add(StickerData(R.drawable.people_emoji_032))
        result.add(StickerData(R.drawable.people_emoji_033))
        result.add(StickerData(R.drawable.people_emoji_034))
        result.add(StickerData(R.drawable.people_emoji_035))
        result.add(StickerData(R.drawable.people_emoji_036))
        result.add(StickerData(R.drawable.people_emoji_037))
        result.add(StickerData(R.drawable.people_emoji_038))
        result.add(StickerData(R.drawable.people_emoji_039))
        result.add(StickerData(R.drawable.people_emoji_040))
        result.add(StickerData(R.drawable.people_emoji_041))
        return result
    }

    private fun getAllEmojiEmotion(): ArrayList<StickerData> {
        val result = ArrayList<StickerData>()
        result.add(StickerData(R.drawable.emotion_emoji_000))
        result.add(StickerData(R.drawable.emotion_emoji_001))
        result.add(StickerData(R.drawable.emotion_emoji_002))
        result.add(StickerData(R.drawable.emotion_emoji_003))
        result.add(StickerData(R.drawable.emotion_emoji_004))
        result.add(StickerData(R.drawable.emotion_emoji_005))
        result.add(StickerData(R.drawable.emotion_emoji_006))
        result.add(StickerData(R.drawable.emotion_emoji_007))
        result.add(StickerData(R.drawable.emotion_emoji_008))
        result.add(StickerData(R.drawable.emotion_emoji_009))
        result.add(StickerData(R.drawable.emotion_emoji_010))
        result.add(StickerData(R.drawable.emotion_emoji_011))
        result.add(StickerData(R.drawable.emotion_emoji_012))
        result.add(StickerData(R.drawable.emotion_emoji_013))
        return result
    }

    private fun getAllEmojiCelebration(): ArrayList<StickerData> {
        val result = ArrayList<StickerData>()
        result.add(StickerData(R.drawable.celebration_emoji_000))
        result.add(StickerData(R.drawable.celebration_emoji_001))
        result.add(StickerData(R.drawable.celebration_emoji_002))
        result.add(StickerData(R.drawable.celebration_emoji_003))
        result.add(StickerData(R.drawable.celebration_emoji_004))
        result.add(StickerData(R.drawable.celebration_emoji_005))
        result.add(StickerData(R.drawable.celebration_emoji_006))
        result.add(StickerData(R.drawable.celebration_emoji_007))
        result.add(StickerData(R.drawable.celebration_emoji_008))
        result.add(StickerData(R.drawable.celebration_emoji_009))
        result.add(StickerData(R.drawable.celebration_emoji_010))
        result.add(StickerData(R.drawable.celebration_emoji_011))
        result.add(StickerData(R.drawable.celebration_emoji_012))
        result.add(StickerData(R.drawable.celebration_emoji_013))
        return result
    }

    private fun getAllEmojiNature(): ArrayList<StickerData> {
        val result = ArrayList<StickerData>()
        result.add(StickerData(R.drawable.nature_emoji_000))
        result.add(StickerData(R.drawable.nature_emoji_001))
        result.add(StickerData(R.drawable.nature_emoji_002))
        result.add(StickerData(R.drawable.nature_emoji_003))
        result.add(StickerData(R.drawable.nature_emoji_004))
        result.add(StickerData(R.drawable.nature_emoji_005))
        result.add(StickerData(R.drawable.nature_emoji_006))
        result.add(StickerData(R.drawable.nature_emoji_007))
        result.add(StickerData(R.drawable.nature_emoji_008))
        result.add(StickerData(R.drawable.nature_emoji_009))
        result.add(StickerData(R.drawable.nature_emoji_010))
        result.add(StickerData(R.drawable.nature_emoji_011))
        result.add(StickerData(R.drawable.nature_emoji_012))
        result.add(StickerData(R.drawable.nature_emoji_013))

        return result
    }

    private fun getAllEmojiActivity(): ArrayList<StickerData> {
        val result = ArrayList<StickerData>()
        result.add(StickerData(R.drawable.activity_emoji_000))
        result.add(StickerData(R.drawable.activity_emoji_001))
        result.add(StickerData(R.drawable.activity_emoji_002))
        result.add(StickerData(R.drawable.activity_emoji_003))
        result.add(StickerData(R.drawable.activity_emoji_004))
        result.add(StickerData(R.drawable.activity_emoji_005))
        result.add(StickerData(R.drawable.activity_emoji_006))
        result.add(StickerData(R.drawable.activity_emoji_007))
        result.add(StickerData(R.drawable.activity_emoji_008))
        result.add(StickerData(R.drawable.activity_emoji_009))
        result.add(StickerData(R.drawable.activity_emoji_010))
        result.add(StickerData(R.drawable.activity_emoji_011))
        result.add(StickerData(R.drawable.activity_emoji_012))
        result.add(StickerData(R.drawable.activity_emoji_013))

        return result
    }

    private fun getAllEmojiFood(): ArrayList<StickerData> {
        val result = ArrayList<StickerData>()
        result.add(StickerData(R.drawable.food_emoji_000))
        result.add(StickerData(R.drawable.food_emoji_001))
        result.add(StickerData(R.drawable.food_emoji_002))
        result.add(StickerData(R.drawable.food_emoji_003))
        result.add(StickerData(R.drawable.food_emoji_004))
        result.add(StickerData(R.drawable.food_emoji_005))
        result.add(StickerData(R.drawable.food_emoji_006))
        result.add(StickerData(R.drawable.food_emoji_007))
        result.add(StickerData(R.drawable.food_emoji_008))
        result.add(StickerData(R.drawable.food_emoji_009))
        result.add(StickerData(R.drawable.food_emoji_010))
        result.add(StickerData(R.drawable.food_emoji_011))
        result.add(StickerData(R.drawable.food_emoji_012))
        result.add(StickerData(R.drawable.food_emoji_013))


        return result
    }
}