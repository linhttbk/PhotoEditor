package com.linhtt.photoeditor.view.text.edit

import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.example.core.custom.VerticalSpacesItemDecoration
import com.linhtt.photoeditor.AppAction
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.text.TextStickViewModel
import kotlinx.android.synthetic.main.fragment_color_page.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class ColorPageFragment  :
    BaseFragment<TextStickViewModel, com.linhtt.photoeditor.databinding.FragmentColorPageBinding>(TextStickViewModel::class) {


    companion object {
        fun newInstance(): ColorPageFragment = ColorPageFragment()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_color_page
    }


    override fun initView() {
        super.initView()
        rcvColor.layoutManager = GridLayoutManager(context,7)
        rcvColor.addItemDecoration(VerticalSpacesItemDecoration(10,true,column = 7))
    }

    override fun initData(viewModel: TextStickViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        viewModel.colorAdapter.setOnItemClickListener(object :BaseRecycleViewAdapter.ItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                Log.e("colorAdapter","click")
                bus.post(AppAction.ACTION_CHANGE_COLOR.setData( viewModel.colorAdapter.getItem(position)))
            }

        })

    }

    override fun initViewModel(): Lazy<TextStickViewModel> {
        return viewModelByClass(TextStickViewModel::class) { parametersOf("") }

    }
}