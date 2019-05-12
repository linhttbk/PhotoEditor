package com.linhtt.photoeditor.view.rotate

import android.widget.SeekBar
import com.example.core.BaseFragment
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.editor.EditActivity
import com.linhtt.photoeditor.view.editor.EditorViewModel
import kotlinx.android.synthetic.main.fragment_shadow.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ShadowFragment : BaseFragment<ShadowViewModel, com.linhtt.photoeditor.databinding.FragmentShadowBinding>(ShadowViewModel::class) {

    val shareViewModel: EditorViewModel by sharedViewModel()

    override fun getLayoutResourceId(): Int = R.layout.fragment_shadow

    companion object {
        fun newInstance(): ShadowFragment = ShadowFragment()
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

    override fun initData(viewModel: ShadowViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as EditActivity).showEditorView(false)
    }
}