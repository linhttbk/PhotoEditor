package com.linhtt.photoeditor.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.linhtt.photoeditor.view.sticker.StickerPageFragment

class StickerPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {


    override fun getItem(p0: Int): Fragment = StickerPageFragment.newInstance(p0)

    override fun getCount(): Int = 6
    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }
}