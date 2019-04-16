package com.linhtt.photoeditor.view.sticker

import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.example.core.custom.VerticalSpacesItemDecoration
import com.linhtt.photoeditor.AppAction
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.home.HomeActivity
import com.sticker.DrawableSticker
import kotlinx.android.synthetic.main.fragment_sticker_page.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class StickerPageFragment :
    BaseFragment<StickerPageViewModel, com.linhtt.photoeditor.databinding.FragmentStickerPageBinding>(
        StickerPageViewModel::class
    ) {
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

    override fun initData(viewModel: StickerPageViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        mBinding.viewModel?.adapter?.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val stickerData = mBinding.viewModel?.adapter?.getItem(position)
                if (stickerData != null) {

                    if (activity != null) {
                        bus.post(AppAction.ACTION_ADD_STICKER.setData( stickerData.icon))
                    }
                }
            }
        })
    }

    override fun initViewModel(): Lazy<StickerPageViewModel> {
        return viewModelByClass(StickerPageViewModel::class) { parametersOf(position) }
    }
}