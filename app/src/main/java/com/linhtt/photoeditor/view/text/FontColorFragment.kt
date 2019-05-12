package com.linhtt.photoeditor.view.text

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.core.BaseFragment
import com.linhtt.photoeditor.AppAction
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.data.model.ColorText
import com.linhtt.photoeditor.data.model.Font
import com.linhtt.photoeditor.view.editor.EditActivity
import com.squareup.otto.Subscribe
import com.sticker.TextSticker
import kotlinx.android.synthetic.main.fragment_font_color_text.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class FontColorFragment :
    BaseFragment<FontColorViewModel, com.linhtt.photoeditor.databinding.FragmentFontColorTextBinding>(FontColorViewModel::class) {
    var currentEdit: TextSticker? = null

    companion object {
        fun newInstance(textSticker: TextSticker?): FontColorFragment {
            val fragment = FontColorFragment()
            fragment.currentEdit = textSticker
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        if (mBinding.viewModel != null) {

        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_font_color_text
    }

    override fun initView() {
        super.initView()
        imvApply.setOnClickListener {
            activity?.onBackPressed()
        }
        imvCancel.setOnClickListener {
            activity?.onBackPressed()
        }
        imvFont.setOnClickListener {
            pagerFontColor.currentItem = 0
        }
        imvColor.setOnClickListener {
            pagerFontColor.currentItem = 1
        }
        imvBackground.setOnClickListener {
            pagerFontColor.currentItem = 2
        }
    }


    override fun initData(viewModel: FontColorViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        viewModel.loadData()


    }

    override fun initViewModel(): Lazy<FontColorViewModel> {
        return viewModelByClass(FontColorViewModel::class) { parametersOf(childFragmentManager) }
    }

    @Subscribe
    fun onAppAction(appAction: AppAction) {

        when (appAction) {
            AppAction.ACTION_CHANGE_COLOR -> {

                val data = appAction.getData(ColorText::class.java)
                Log.e("change", data.color.toString())
                val resID = resources.getIdentifier(
                    "color" + data.color,
                    "color", context?.packageName
                )
                currentEdit?.setTextColor(ContextCompat.getColor(context!!, resID))
                bus.post(AppAction.ACTION_UPDATE)

            }
            AppAction.ACTION_CHANGE_FONT -> {
                val data = appAction.getData(Font::class.java)
                val am = context?.assets
                val typeface = Typeface.createFromAsset(am, data.fileName + ".ttf")
                currentEdit?.setTypeface(typeface)
                bus.post(AppAction.ACTION_UPDATE)
            }
            AppAction.ACTION_CHANGE_BACKGROUND -> {
                val data = appAction.getData(ColorText::class.java)
                Log.e("change", data.color.toString())
                val resID = resources.getIdentifier(
                    "color" + data.color,
                    "color", context?.packageName
                )
                currentEdit?.setColorBg(ContextCompat.getColor(context!!, resID))
                bus.post(AppAction.ACTION_UPDATE)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (activity != null) {
            Log.e("destroy","destroy")
            (activity as EditActivity).setShow(false)
        }
    }
}