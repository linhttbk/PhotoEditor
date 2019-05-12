package com.linhtt.photoeditor.view.preview

import android.net.Uri
import com.example.core.BaseFragment
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.databinding.FragmentPreviewBinding
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class PreviewFragment : BaseFragment<PreviewViewModel, FragmentPreviewBinding>(PreviewViewModel::class) {
    var uri: Uri? = null

    companion object {
        fun newInstance(uri: Uri): PreviewFragment {
            val fragment = PreviewFragment()
            fragment.uri = uri
            return fragment
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_preview
    }

    override fun initData(viewModel: PreviewViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }

    override fun initViewModel(): Lazy<PreviewViewModel> {
        return viewModelByClass(PreviewViewModel::class) { parametersOf(uri) }
    }

}