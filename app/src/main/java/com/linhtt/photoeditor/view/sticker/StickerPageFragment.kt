package com.linhtt.photoeditor.view.sticker

import android.support.v7.widget.GridLayoutManager
import com.example.core.BaseFragment
import com.example.core.custom.VerticalSpacesItemDecoration
import com.linhtt.photoeditor.R
import kotlinx.android.synthetic.main.fragment_sticker_page.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class StickerPageFragment :
    BaseFragment<StickerViewModel, com.linhtt.photoeditor.databinding.FragmentStickerPageBinding>(StickerViewModel::class) {
    var position: Int = 0

    companion object {
        fun newInstance(position: Int): StickerPageFragment {
            val fragment = StickerPageFragment()
            fragment.position = position
            return fragment
        }
    }


    override fun getLayoutResourceId(): Int = R.layout.fragment_sticker_page

    override fun initView() {
        super.initView()
        rcvSticker.layoutManager = GridLayoutManager(context, 7)
        rcvSticker.addItemDecoration(VerticalSpacesItemDecoration(10, true, 7))
    }

    override fun initData(viewModel: StickerViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }

    override fun initViewModel(): Lazy<StickerViewModel> {
        return viewModelByClass(StickerViewModel::class) { parametersOf(position) }
    }
}