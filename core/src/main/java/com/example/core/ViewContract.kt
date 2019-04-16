package com.example.core

import android.support.v4.app.Fragment

interface ViewContract {
    fun switchFragment(fragment: Fragment, hasAnim: Boolean)
    fun switchFragment(fragment: Fragment, root: Int, hasAnim: Boolean)
    fun showDialogLoading(isShow: Boolean)
    fun enableRefresh(isEnable: Boolean)

}