package com.linhtt.photoeditor.view.editor

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewStructure
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.example.core.ext.view.slideDown
import com.example.core.ext.view.slideUp
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.crop.CropFragment
import com.linhtt.photoeditor.view.home.HomeViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.LoadImageCallback
import kotlinx.android.synthetic.main.fragment_photo_editor.*
import kotlinx.android.synthetic.main.layout_add_sticker.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf


class PhotoEditorFragment :
    BaseFragment<EditorViewModel, com.linhtt.photoeditor.databinding.FragmentPhotoEditorBinding>(EditorViewModel::class),
    BaseRecycleViewAdapter.ItemClickListener, LoadImageCallback {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    var path: String = ""
    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_photo_editor
    }

    companion object {
        fun newInstance(path: String): PhotoEditorFragment {
            val fragment = PhotoEditorFragment()
            fragment.path = path
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        enableRefresh(false)
        gpuImage.setScaleType(GPUImage.ScaleType.CENTER_CROP)
        rcvListFilter.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcvFilter.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        imvBack.setOnClickListener {
            if (activity != null)
                activity!!.onBackPressed()
        }
        gpuImage.setShowLoading(true)
        gpuImage.getGPUImage().setCallback(this)
        tvSave.setOnClickListener {
            rcvListFilter.slideUp()
            rcvFilter.slideDown()
        }
        imvClose.setOnClickListener {
            bottomEditor.visibility = View.GONE
        }


    }

    override fun initData(viewModel: EditorViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        mBinding.shareModel = homeViewModel
        viewModel.adapter.setOnItemClickListener(this)
        viewModel.visibility.observe(this, Observer {
            Log.e("observe", "$it")
            if (it == View.VISIBLE) {
                rcvListFilter.slideDown()
                rcvFilter.slideUp()
            } else {
                rcvListFilter.slideUp()
            }
        })
    }

    override fun initViewModel(): Lazy<EditorViewModel> {
        return viewModelByClass(EditorViewModel::class) { parametersOf(path, fragmentManager) }
    }

    override fun onItemClick(view: View, position: Int) {
        when (position) {
            0 -> {
                val pathSave = homeViewModel.loadBitmapFromCache()
                if (pathSave.isNullOrEmpty()) {
                    switchFragment(CropFragment.newInstance(path), true)
                } else {
                    switchFragment(CropFragment.newInstance(pathSave), true)
                }
            }
            1 -> {
                if (mBinding.viewModel != null) {
                    mBinding.viewModel!!.visibility.value = View.VISIBLE

                }
            }
            2 -> addLayoutSticker()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeViewModel.clear()
    }

    override fun onLoadingSuccess() {
        if (activity != null) {
            activity!!.runOnUiThread {
                showDialogLoading(false)
            }
        }

    }

    override fun onLoadError() {
        if (activity != null) {
            activity!!.runOnUiThread {
                showDialogLoading(false)
            }
        }
    }

    override fun onStartLoading() {

        if (activity != null) {
            activity!!.runOnUiThread {
                showDialogLoading(true)
            }
        }
    }


    private fun addLayoutSticker() {
        bottomEditor.visibility = View.VISIBLE
    }

}