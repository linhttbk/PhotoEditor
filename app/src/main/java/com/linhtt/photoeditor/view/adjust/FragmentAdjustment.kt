package com.linhtt.photoeditor.view.adjust

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.SeekBar
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.custom.GPUImageFilterTools
import com.linhtt.photoeditor.view.editor.EditActivity
import com.linhtt.photoeditor.view.editor.EditorViewModel
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter
import kotlinx.android.synthetic.main.fragment_adjustment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FragmentAdjustment :
    BaseFragment<AdjustViewModel, com.linhtt.photoeditor.databinding.FragmentAdjustmentBinding>(AdjustViewModel::class) {
    var filterAdjuster: GPUImageFilterTools.FilterAdjuster? = null
    val shareViewModel: EditorViewModel by sharedViewModel()

    companion object {
        fun newInstance(): FragmentAdjustment {
            val fragmentAdjustment = FragmentAdjustment()
            return fragmentAdjustment
        }
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_adjustment

    override fun initView() {
        super.initView()
        rcvAdjust.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun initData(viewModel: AdjustViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel

        viewModel.adapter.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val filter: GPUImageFilter
                when (position) {

                    0 -> {
                        filter = GPUImageBrightnessFilter()
                        viewModel.selectedFilter(filter, shareViewModel)
                    }
                    1 -> {
                        filter = GPUImageContrastFilter()
                        viewModel.selectedFilter(filter, shareViewModel)
                    }
                    2 -> {
                        filter = GPUImageSaturationFilter()
                        viewModel.selectedFilter(filter, shareViewModel)
                    }
                    3 -> {
                        filter = GPUImageSaturationFilter()
                        viewModel.selectedFilter(filter, shareViewModel)
                    }

                }

                (activity as EditActivity).applyFilterGroup(shareViewModel.group)
                viewModel.adapter.updateSelected(position)
                seekBar.progress = shareViewModel.listFilter[viewModel.currentFilter?.javaClass?.name]!!


            }
        })
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val selected = viewModel.adapter.getSelectedPosition()
                if (selected != -1) {
                    if (viewModel.adjust(seekBar!!.progress)) {
                        (activity as EditActivity).applyAdjust()
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val selected = viewModel.adapter.getSelectedItem()
                if (selected != null) {
                    selected.percent = seekBar!!.progress
                    viewModel.updateAdjustUser(shareViewModel,seekBar.progress)

                }
            }

        })

    }


}