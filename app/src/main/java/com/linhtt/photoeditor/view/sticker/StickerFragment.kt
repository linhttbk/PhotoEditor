package com.linhtt.photoeditor.view.sticker

import com.example.core.BaseFragment
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.editor.EditActivity
import kotlinx.android.synthetic.main.fragment_sticker.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class StickerFragment :
    BaseFragment<StickerViewModel, com.linhtt.photoeditor.databinding.FragmentStickerBinding>(StickerViewModel::class) {

    companion object {
        fun newInstance(): StickerFragment {
            val fragment = StickerFragment()
            return fragment
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_sticker
    }

    override fun initView() {
        super.initView()
        imvClose.setOnClickListener{
            activity?.onBackPressed()
        }
        imvApply.setOnClickListener{

            activity?.onBackPressed()
        }
    }

    override fun initData(viewModel: StickerViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }

    override fun initViewModel(): Lazy<StickerViewModel> {
        return viewModelByClass(StickerViewModel::class) { parametersOf(fragmentManager) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as EditActivity).showEditorView(false)
    }
}