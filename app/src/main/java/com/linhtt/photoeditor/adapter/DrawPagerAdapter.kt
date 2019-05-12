package com.linhtt.photoeditor.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.linhtt.photoeditor.view.draw.page.ColorDrawFragment
import com.linhtt.photoeditor.view.draw.page.SizeDrawFragment

class DrawPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    val items = listOf(SizeDrawFragment.newInstance(),ColorDrawFragment.newInstance())

    override fun getItem(p0: Int): Fragment {
        return items[p0]
    }

    override fun getCount(): Int = items.size
    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }
}