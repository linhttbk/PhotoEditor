package com.linhtt.photoeditor.view.editor

import android.arch.lifecycle.Observer
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.core.BaseActivity
import com.example.core.BaseRecycleViewAdapter
import com.example.core.ext.view.slideDown
import com.example.core.ext.view.slideUp
import com.linhtt.photoeditor.AppAction
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.adjust.FragmentAdjustment
import com.linhtt.photoeditor.view.crop.CropFragment
import com.linhtt.photoeditor.view.sticker.StickerFragment
import com.squareup.otto.Subscribe
import com.sticker.DrawableSticker
import com.sticker.Sticker
import com.sticker.StickerView
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.LoadImageCallback
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf
import java.io.File

class EditActivity :
    BaseActivity<EditorViewModel, com.linhtt.photoeditor.databinding.ActivityEditBinding>(EditorViewModel::class),
    BaseRecycleViewAdapter.ItemClickListener, LoadImageCallback, StickerView.OnStickerOperationListener {

    private var path: String = ""
    override fun getRoot(): Int = 0
    override fun getLayoutResourceId(): Int = R.layout.activity_edit


    override fun onStickerAdded(sticker: Sticker) {

    }

    override fun onStickerClicked(sticker: Sticker) {
    }

    override fun onStickerDeleted(sticker: Sticker) {
    }

    override fun onStickerDragFinished(sticker: Sticker) {

    }

    override fun onStickerTouchedDown(sticker: Sticker) {
    }

    override fun onStickerZoomFinished(sticker: Sticker) {

    }

    override fun onStickerFlipped(sticker: Sticker) {

    }

    override fun onStickerDoubleTapped(sticker: Sticker) {

    }

    override fun initView() {
        super.initView()
        path = intent.extras.getString("path")
        enableRefresh(false)
        gpuImage.setScaleType(GPUImage.ScaleType.CENTER_CROP)
        rcvListFilter.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcvFilter.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imvBack.setOnClickListener {
            onBackPressed()
        }
        gpuImage.setShowLoading(true)
        gpuImage.getGPUImage().setCallback(this)
        gpuImage.setStickerTouch(this)
        tvSave.setOnClickListener {
            rcvListFilter.slideUp()
            rcvFilter.slideDown()
        }
    }

    fun showStickerView() {
        gpuImage.showStickerView()
    }

    override fun onResume() {
        super.onResume()
        gpuImage.showStickerView()
    }

    override fun initData(viewModel: EditorViewModel) {
        super.initData(viewModel)

        binding.viewModel = viewModel
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
        viewModel.currentIndex.observe(this, Observer {
            path = viewModel.loadBitmapFromCache()
            gpuImage.setImage(File(path))
        })
    }

    override fun initViewModel(): Lazy<EditorViewModel> {
        return viewModelByClass(EditorViewModel::class) { parametersOf(path, supportFragmentManager) }
    }

    override fun onItemClick(view: View, position: Int) {
        if (position == 8) {
            gpuImage.showStickerView()
        } else {
            gpuImage.hideStickerView()
        }
        when (position) {
            0 -> {
                val pathSave = binding.viewModel?.loadBitmapFromCache()
                if (!pathSave.isNullOrEmpty()) {
                    switchFragment(CropFragment.newInstance(pathSave), true)
                }
            }
            1 -> {
                if (binding.viewModel != null) {
                    binding.viewModel!!.visibility.value = View.VISIBLE

                }
            }
            2 -> {
                showAdjustmentTool()
            }
            8 -> {
                addLayoutSticker()
            }
        }
    }


    @Subscribe
    fun onAppAction(appAction: AppAction) {
        when (appAction) {
            AppAction.ACTION_ADD_STICKER -> {
                val icon = appAction.getData(Int::class.java)
                val sticker = DrawableSticker(ContextCompat.getDrawable(this, icon))
                gpuImage.addSticker(sticker)
            }
        }
    }

    override fun onLoadingSuccess() {
        runOnUiThread {
            showDialogLoading(false)
        }


    }

    override fun onLoadError() {
        runOnUiThread {
            showDialogLoading(false)
        }
    }


    override fun onStartLoading() {

        runOnUiThread {
            showDialogLoading(true)

        }
    }

    private fun addLayoutSticker() {
        switchFragment(StickerFragment.newInstance(), R.id.tool_layout, false)
    }

    private fun showAdjustmentTool() {
        switchFragment(FragmentAdjustment.newInstance(), R.id.tool_layout, false)
    }
}
