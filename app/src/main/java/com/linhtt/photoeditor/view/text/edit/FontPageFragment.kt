package com.linhtt.photoeditor.view.text.edit

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.AppAction
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.text.TextStickViewModel
import kotlinx.android.synthetic.main.fragment_font_page.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class FontPageFragment :
    BaseFragment<TextStickViewModel, com.linhtt.photoeditor.databinding.FragmentFontPageBinding>(TextStickViewModel::class) {


    companion object {
        fun newInstance(): FontPageFragment = FontPageFragment()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_font_page
    }

    override fun initView() {
        super.initView()
        rcvFont.layoutManager = LinearLayoutManager(context)
    }


    override fun initData(viewModel: TextStickViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        viewModel.fontAdapter.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                bus.post(AppAction.ACTION_CHANGE_FONT.setData( viewModel.fontAdapter.getItem(position)))
            }
        })

    }

    override fun initViewModel(): Lazy<TextStickViewModel> {
        return viewModelByClass(TextStickViewModel::class) { parametersOf("") }

    }
}