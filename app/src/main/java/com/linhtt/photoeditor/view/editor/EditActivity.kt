package com.linhtt.photoeditor.view.editor

import android.app.Activity
import android.arch.lifecycle.Observer
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.core.BaseActivity
import com.example.core.BaseRecycleViewAdapter
import com.example.core.ext.view.showToast
import com.example.core.ext.view.slideDown
import com.example.core.ext.view.slideUp
import com.linhtt.photoeditor.AppAction
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.custom.GPUImageColorFilter
import com.linhtt.photoeditor.view.adjust.FragmentAdjustment
import com.linhtt.photoeditor.view.blend.BlendFragment
import com.linhtt.photoeditor.view.blur.BlurFragment
import com.linhtt.photoeditor.view.crop.CropFragment
import com.linhtt.photoeditor.view.draw.DrawFragment
import com.linhtt.photoeditor.view.filter.FilterColorFragment
import com.linhtt.photoeditor.view.preview.PreviewFragment
import com.linhtt.photoeditor.view.rotate.ConvolutionFragment
import com.linhtt.photoeditor.view.rotate.ShadowFragment
import com.linhtt.photoeditor.view.sticker.StickerFragment
import com.linhtt.photoeditor.view.text.AddTextFragment
import com.linhtt.photoeditor.view.text.FontColorFragment
import com.squareup.otto.Subscribe
import com.sticker.DrawableSticker
import com.sticker.Sticker
import com.sticker.StickerView
import com.sticker.TextSticker
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.GPUImageView
import jp.co.cyberagent.android.gpuimage.LoadImageCallback
import jp.co.cyberagent.android.gpuimage.filter.*
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf
import java.io.File


class EditActivity :
    BaseActivity<EditorViewModel, com.linhtt.photoeditor.databinding.ActivityEditBinding>(EditorViewModel::class),
    BaseRecycleViewAdapter.ItemClickListener, LoadImageCallback, StickerView.OnStickerOperationListener {
    override fun onClickEdit(sticker: Sticker) {
        if (sticker is TextSticker) {
            showAddTextFragment(sticker)
        }
    }

    private var path: String = ""
    private var isShow = false

    fun setShow(isShow: Boolean) {
        this.isShow = isShow
    }

    override fun getRoot(): Int = 0
    override fun getLayoutResourceId(): Int = R.layout.activity_edit

    override fun onStickerAdded(sticker: Sticker) {

    }

    override fun onStickerClicked(sticker: Sticker) {
        if (sticker is TextSticker) {
            val f = supportFragmentManager.findFragmentById(R.id.tool_layout)
            if (f != null) {
                Log.e("state", f.isVisible.toString() + f.isHidden.toString() + f.isResumed.toString() + f.isDetached)
                if (!f.isVisible)
                    switchFragment(FontColorFragment.newInstance(sticker), R.id.tool_layout, false)

            }

        }
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
            //            gpuImage.saveToPictures(
//                "PhotoAppp",
//                System.currentTimeMillis().toString() + ".png"
//            ) { uri -> Log.e("save", uri?.toString()) }
            gpuImage.saveBitmapToFile()
        }
        gpuImage.setSaveListener(GPUImageView.OnPictureSavedListener {
            showToast("saved image successfully!")
                switchFragment(PreviewFragment.newInstance(it),true)
        })
    }


    override fun onResume() {
        super.onResume()
        gpuImage.showEditorView(false)
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
        Log.e("path", path)
    }

    override fun initViewModel(): Lazy<EditorViewModel> {
        return viewModelByClass(EditorViewModel::class) { parametersOf(path, supportFragmentManager) }
    }

    override fun onItemClick(view: View, position: Int) {
        if (position == 3 || position == 4) {
            gpuImage.showEditorView(false)
        } else if (position == 5) {
            gpuImage.showEditorView(true)
        } else {
            gpuImage.hideEditorView()
        }

        when (position) {
            0 -> {
                val pathSave = binding.viewModel?.loadBitmapFromCache()
                if (!pathSave.isNullOrEmpty()) {
                    switchFragment(CropFragment.newInstance(pathSave), true)
                }
            }
//            1 -> {
////                if (binding.viewModel != null) {
////                    binding.viewModel!!.visibility.value = View.VISIBLE
////
////                }
//                showFilterTool()
//            }
////            5 -> {
////                showBlendTool()
////            }

            1 -> {
                showAdjustmentTool()
            }
            2 -> {
                showBlurFragment()
            }

            3 -> {

                addLayoutSticker()

            }
            4 -> {
                showAddTextFragment(null)
            }
            5 -> {
                showDrawFragment()
            }
            6 -> {
                showEdgeFragment()
            }
            7 ->{
                showShadowFragment()
            }
        }
    }

    private fun getBitmap() {
        // rlImage.setDra
    }


    @Subscribe
    fun onAppAction(appAction: AppAction) {
        when (appAction) {
            AppAction.ACTION_ADD_STICKER -> {
                val icon = appAction.getData(Int::class.java)
                val sticker = DrawableSticker(ContextCompat.getDrawable(this, icon))
                gpuImage.addSticker(sticker)
            }
            AppAction.ACTION_ADD_TEXT_STICKER -> {
                hideKeyboard()
                val text = appAction.getData(String::class.java)
                val sticker = TextSticker(this).setText(text).resizeText()
                Log.e("sticker", text)
                gpuImage.addSticker(sticker)
            }
            AppAction.ACTION_EDIT_TEXT_STICKER -> {
                hideKeyboard()
                gpuImage.stickerView.invalidate()
            }

            AppAction.ACTION_UPDATE -> {
                gpuImage.stickerView.invalidate()
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

    fun showStickerView() {
        gpuImage.showStickerView()
    }

    fun showEditorView(enableDraw: Boolean) {
        gpuImage.showEditorView(enableDraw)
    }

    private fun showAddTextFragment(textSticker: TextSticker?) {
        switchFragment(AddTextFragment.newInstance(textSticker), R.id.tool_layout, false)

    }

    private fun showBlurFragment() {
        if (binding.viewModel != null) {
            binding.viewModel?.addFilterToGroup(GPUImageGaussianBlurFilter())
            applyFilterGroup(binding.viewModel!!.getFilterGroup())
            switchFragment(BlurFragment.newInstance(), R.id.tool_layout, false)
        }

    }

    private fun showEdgeFragment() {
        if (binding.viewModel != null) {
            binding.viewModel?.addFilterToGroup(GPUImageThresholdEdgeDetectionFilter())
            applyFilterGroup(binding.viewModel!!.getFilterGroup())
            switchFragment(ConvolutionFragment.newInstance(), R.id.tool_layout, false)
        }
    }

    private fun showShadowFragment() {
        if (binding.viewModel != null) {
            binding.viewModel?.addFilterToGroup(GPUImageHighlightShadowFilter())
            applyFilterGroup(binding.viewModel!!.getFilterGroup())
            switchFragment(ShadowFragment.newInstance(), R.id.tool_layout, false)
        }
    }

    private fun addLayoutSticker() {
        switchFragment(StickerFragment.newInstance(), R.id.tool_layout, false)
    }

    private fun showAdjustmentTool() {
        switchFragment(FragmentAdjustment.newInstance(), R.id.tool_layout, false)
    }

    private fun showDrawFragment() {
        switchFragment(DrawFragment.newInstance(), R.id.tool_layout, false)
    }

    private fun showFilterTool() {
        if (binding.viewModel != null) {
            binding.viewModel?.addFilterToGroup(GPUImageColorFilter())
            applyFilterGroup(binding.viewModel!!.getFilterGroup())
            switchFragment(FilterColorFragment.newInstance(), R.id.tool_layout, false)
        }
    }

    private fun showBlendTool() {
        switchFragment(BlendFragment.newInstance(path), R.id.tool_layout, false)
    }

    fun switchFilterTo(filter: GPUImageFilter) {
        if (gpuImage.filter == null || gpuImage.filter.javaClass != filter.javaClass) {
            Log.e("Change Filter", filter.javaClass.simpleName)
            gpuImage.filter = filter
        }
    }

    fun applyAdjust() {
        gpuImage.requestRender()
    }

    fun applyFilterGroup(group: GPUImageFilterGroup) {
        gpuImage.filter = group
        gpuImage.requestRender()

    }

    fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(
            com.example.core.R.anim.default_anim,
            com.example.core.R.anim.default_anim,
            com.example.core.R.anim.default_anim,
            com.example.core.R.anim.default_anim
        )
        transaction.show(fragment)
        transaction.commit()
    }

    fun changeDrawColor(color: Int) {
        gpuImage.stickerView.setDrawColor(color)
    }

    fun changeSizeDraw(size: Float) {
        gpuImage.stickerView.setSizeDraw(size)
    }

    fun changeEraserMode(eraser:Boolean) {
        gpuImage.stickerView.changeEraserMode(eraser)
    }
}
