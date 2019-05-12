package com.linhtt.photoeditor.view.text

import android.graphics.Color
import com.example.core.BaseFragment
import com.example.core.ext.view.showToast
import com.linhtt.photoeditor.AppAction
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.editor.EditActivity
import com.sticker.TextSticker
import kotlinx.android.synthetic.main.fragment_add_text.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class AddTextFragment :
    BaseFragment<TextStickViewModel, com.linhtt.photoeditor.databinding.FragmentAddTextBinding>(TextStickViewModel::class) {
    var text: String = ""
    var currentEdit: TextSticker? = null

    companion object {
        fun newInstance(textSticker: TextSticker?): AddTextFragment {
            val fragment = AddTextFragment()
            fragment.currentEdit = textSticker
            return fragment
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_add_text
    }

    override fun initView() {
        super.initView()
        edtText.requestFocus()
        imvApply.setOnClickListener {
            if (edtText.text.isNullOrEmpty()) {
                showToast("Text can not be empty")
            } else {
                if (currentEdit == null)
                    bus.post(AppAction.ACTION_ADD_TEXT_STICKER.setData(edtText.text.toString()))
                else {
                    currentEdit?.text = edtText.text.toString()
                    bus.post(AppAction.ACTION_EDIT_TEXT_STICKER.setData(edtText.text.toString()))
                }
                activity?.onBackPressed()
            }

        }
        imvCancel.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun initData(viewModel: TextStickViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }

    override fun initViewModel(): Lazy<TextStickViewModel> {
        if (currentEdit == null || currentEdit?.text == null) {
            text = ""
        } else {
            text = currentEdit?.text!!
        }
        return viewModelByClass(TextStickViewModel::class) { parametersOf(text) }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as EditActivity).showEditorView(false)
    }
}