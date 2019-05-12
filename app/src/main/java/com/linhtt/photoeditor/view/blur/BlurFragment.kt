package com.linhtt.photoeditor.view.blur

import android.util.Log
import android.widget.SeekBar
import com.example.core.BaseFragment
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.editor.EditActivity
import com.linhtt.photoeditor.view.editor.EditorViewModel
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter
import kotlinx.android.synthetic.main.fragment_blur.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class BlurFragment :
    BaseFragment<BlurViewModel, com.linhtt.photoeditor.databinding.FragmentBlurBinding>(BlurViewModel::class) {
    val shareViewModel: EditorViewModel by sharedViewModel()
    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_blur
    }

    companion object {
        fun newInstance(): BlurFragment = BlurFragment()
    }

    override fun initView() {
        super.initView()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                (activity as EditActivity).applyAdjust()
                if (shareViewModel.adjust(seekBar!!.progress)) {
                    (activity as EditActivity).applyAdjust()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        imvClose.setOnClickListener {
            activity?.onBackPressed()

        }
        imvApply.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as EditActivity).showEditorView(false)
    }

    override fun initData(viewModel: BlurViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }



}