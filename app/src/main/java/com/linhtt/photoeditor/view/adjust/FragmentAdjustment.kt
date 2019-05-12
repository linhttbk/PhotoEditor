package com.linhtt.photoeditor.view.adjust

import android.graphics.PointF
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.custom.GPUImageFilterTools
import com.linhtt.photoeditor.view.editor.EditActivity
import com.linhtt.photoeditor.view.editor.EditorViewModel
import jp.co.cyberagent.android.gpuimage.filter.*
import kotlinx.android.synthetic.main.fragment_adjustment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FragmentAdjustment :
    BaseFragment<AdjustViewModel, com.linhtt.photoeditor.databinding.FragmentAdjustmentBinding>(AdjustViewModel::class) {
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
        imvApply.setOnClickListener {
            activity?.onBackPressed()
        }
        imvClose.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun initData(viewModel: AdjustViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel

        viewModel.adapter.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val filter: GPUImageFilter
                when (position) {

                    0 -> {
                        filter = GPUImageBrightnessFilter(1.5f)
                        viewModel.selectedFilter(filter, shareViewModel)
                    }
                    1 -> {
                        filter = GPUImageContrastFilter(2.0f)
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
                    4 -> {
                        filter = GPUImageHueFilter(90.0f)
                        viewModel.selectedFilter(filter, shareViewModel)
                    }
                    5 -> {
                        filter = GPUImageSharpenFilter()
                        viewModel.selectedFilter(filter, shareViewModel)
                    }
                    6 -> {
                        filter = GPUImagePixelationFilter()
                        viewModel.selectedFilter(filter, shareViewModel)
                    }
                    7 -> {
                        filter = GPUImageVignetteFilter( PointF(0.5f, 0.5f),
                            floatArrayOf(0.0f, 0.0f, 0.0f),
                            0.3f,
                            0.75f)
                        viewModel.selectedFilter(filter, shareViewModel)
                    }
                    8 -> {
                        filter = GPUImageSwirlFilter()
                        viewModel.selectedFilter(filter, shareViewModel)
                    }

                }

                (activity as EditActivity).applyFilterGroup(shareViewModel.getFilterGroup())
                viewModel.adapter.updateSelected(position)
                seekBar.progress = shareViewModel.listFilter[shareViewModel.currentFilter?.javaClass?.name]!!


            }
        })
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val selected = viewModel.adapter.getSelectedPosition()
                if (selected != -1) {
                    if (viewModel.adjust(seekBar!!.progress,shareViewModel)) {
                        (activity as EditActivity).applyAdjust()
                        Log.e("currentFilter",shareViewModel.currentFilter?.javaClass.toString())
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
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as EditActivity).showEditorView(false)
    }

}