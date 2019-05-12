package com.linhtt.photoeditor.view.rotate

import android.widget.SeekBar
import com.example.core.BaseFragment
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.editor.EditActivity
import com.linhtt.photoeditor.view.editor.EditorViewModel
import kotlinx.android.synthetic.main.fragment_rotate.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ConvolutionFragment : BaseFragment<ConvolutionViewModel, com.linhtt.photoeditor.databinding.FragmentRotateBinding>(ConvolutionViewModel::class) {

    val shareViewModel: EditorViewModel by sharedViewModel()

    override fun getLayoutResourceId(): Int = R.layout.fragment_rotate

    companion object {
        fun newInstance(): ConvolutionFragment = ConvolutionFragment()
    }

    override fun initView() {
        super.initView()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
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

    override fun initData(viewModel: ConvolutionViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as EditActivity).showEditorView(false)
    }
}