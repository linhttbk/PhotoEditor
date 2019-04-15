package com.example.core

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

class FragmentHelper(var fragmentManager: FragmentManager, var root: Int) {

    fun  switchFragment(fragment: Fragment, hasAnim: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        if (hasAnim) {
            transaction.setCustomAnimations(R.anim.from_right, R.anim.to_left, R.anim.from_left, R.anim.to_right)
        } else {
            transaction.setCustomAnimations(
                R.anim.default_anim,
                R.anim.default_anim,
                R.anim.default_anim,
                R.anim.default_anim
            )
        }
        if (root == 0) root = R.id.root
        transaction.replace(root, fragment)
        transaction.addToBackStack(System.currentTimeMillis().toString())
        transaction.commit()
    }
    fun  switchFragment(fragment: Fragment,root:Int, hasAnim: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        if (hasAnim) {
            transaction.setCustomAnimations(R.anim.from_right, R.anim.to_left, R.anim.from_left, R.anim.to_right)
        } else {
            transaction.setCustomAnimations(
                R.anim.default_anim,
                R.anim.default_anim,
                R.anim.default_anim,
                R.anim.default_anim
            )
        }
        transaction.replace(root, fragment)
        transaction.addToBackStack(System.currentTimeMillis().toString())
        transaction.commit()
    }

}