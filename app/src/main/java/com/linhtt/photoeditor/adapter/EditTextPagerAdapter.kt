package com.linhtt.photoeditor.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.linhtt.photoeditor.view.text.edit.BackgroundPageFragment
import com.linhtt.photoeditor.view.text.edit.ColorPageFragment
import com.linhtt.photoeditor.view.text.edit.FontPageFragment

class EditTextPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    val items = listOf(FontPageFragment.newInstance(),ColorPageFragment.newInstance(),BackgroundPageFragment.newInstance())
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Fragment {
        return items[p0]
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }

}