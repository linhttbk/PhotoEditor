package com.linhtt.photoeditor.view.filter

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.editor.EditActivity
import com.linhtt.photoeditor.view.editor.EditorViewModel
import kotlinx.android.synthetic.main.fragment_filter_color.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FilterColorFragment :
    BaseFragment<FilterViewModel, com.linhtt.photoeditor.databinding.FragmentFilterColorBinding>(FilterViewModel::class) {
    val shareViewModel: EditorViewModel by sharedViewModel()

    companion object {
        fun newInstance(): FilterColorFragment {
            val fragment = FilterColorFragment()
            return fragment
        }
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_filter_color

    override fun initView() {
        super.initView()
        rcvFilterColor.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun initData(viewModel: FilterViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        viewModel.adapter.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val item = viewModel.adapter.getItem(position)
                val typeFilter = item.typeFilter
                Log.e("Click",typeFilter.matrix[0].toString())
                if(viewModel.adjustColorMatrix(shareViewModel, typeFilter.matrix, typeFilter.offset)){
                   // (activity as EditActivity).applyFilterGroup(shareViewModel.group)
                    (activity as EditActivity).applyAdjust()
                    Log.e("currentFilter",shareViewModel.currentFilter?.javaClass.toString())
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as EditActivity).showEditorView(false)
    }
}