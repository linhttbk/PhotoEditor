package com.linhtt.photoeditor.view.adjust

import android.support.v7.widget.LinearLayoutManager
import com.example.core.BaseFragment
import com.linhtt.photoeditor.R
import kotlinx.android.synthetic.main.fragment_adjustment.*

class FragmentAdjustment:BaseFragment<AdjustViewModel,com.linhtt.photoeditor.databinding.FragmentAdjustmentBinding>(AdjustViewModel::class) {

    companion object {
        fun newInstance():FragmentAdjustment{
            val fragmentAdjustment = FragmentAdjustment()
            return fragmentAdjustment
        }
    }

    override fun getLayoutResourceId(): Int  = R.layout.fragment_adjustment

    override fun initView() {
        super.initView()
        rcvAdjust.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun initData(viewModel: AdjustViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }

}