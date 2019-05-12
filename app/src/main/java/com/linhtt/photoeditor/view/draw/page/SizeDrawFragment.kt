package com.linhtt.photoeditor.view.draw.page

import android.util.Log
import android.widget.SeekBar
import com.example.core.BaseFragment
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.draw.DrawViewModel
import com.linhtt.photoeditor.view.editor.EditActivity
import kotlinx.android.synthetic.main.fragment_size_draw.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class SizeDrawFragment :
    BaseFragment<DrawViewModel, com.linhtt.photoeditor.databinding.FragmentSizeDrawBinding>(DrawViewModel::class) {
    var mode = 0

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_size_draw
    }

    override fun initView() {
        super.initView()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var value = 12f
                if (progress < 50) {
                    value -= 12f * 0.5f * progress * 100 / (100 * 100)
                } else {
                    value += 12f * 2f * progress * 100 / (100 * 100)
                }

                if (value == 0f) value = 1f
                Log.e("Value", (12f * progress * 100 / (100 * 100)).toString())
                (activity as EditActivity).changeSizeDraw(value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        imvChange.setOnClickListener {
            if (mode == 0) {
                imvChange.setImageResource(R.drawable.icon_eraser_)
                mode = 1

            } else {
                imvChange.setImageResource(R.drawable.icon_draw)
                mode = 0
            }
            (activity as EditActivity).changeEraserMode(mode == 1)
        }
    }

    override fun initData(viewModel: DrawViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }

    override fun initViewModel(): Lazy<DrawViewModel> {
        return viewModelByClass(DrawViewModel::class) { parametersOf(childFragmentManager) }
    }

    companion object {
        fun newInstance(): SizeDrawFragment = SizeDrawFragment()
    }
}