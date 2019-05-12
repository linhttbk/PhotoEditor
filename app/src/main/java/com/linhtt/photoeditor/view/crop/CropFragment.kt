package com.linhtt.photoeditor.view.crop

import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.example.core.FileUtil
import com.example.core.ext.view.showToast
import com.linhtt.photoeditor.BuildConfig
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.custom.CenterLayoutManager
import com.linhtt.photoeditor.custom.ScrollPositionSnap
import com.linhtt.photoeditor.data.model.Ratio
import com.linhtt.photoeditor.view.editor.EditActivity
import com.linhtt.photoeditor.view.editor.EditorViewModel
import com.linhtt.photoeditor.view.home.HomeViewModel
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_crop.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf
import java.io.File

class CropFragment :
    BaseFragment<CropViewModel, com.linhtt.photoeditor.databinding.FragmentCropBinding>(CropViewModel::class),
    BaseRecycleViewAdapter.ItemClickListener, CropImageView.OnCropImageCompleteListener {


    val homeViewModel: EditorViewModel by sharedViewModel()
    var path: String = ""
    var cropPath = ""

    companion object {
        fun newInstance(path: String): CropFragment {
            val fragment = CropFragment()
            fragment.path = path
            return fragment
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_crop
    }

    override fun initView() {
        super.initView()
        enableRefresh(false)
        rcvRatio.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcvRatio.setHasFixedSize(true)
        cropView.setOnCropImageCompleteListener(this)
        imvClose.setOnClickListener {
            activity!!.onBackPressed()
        }
        imvApply.setOnClickListener {

            val photoFile = FileUtil.createImageFile("${System.currentTimeMillis()}.jpg", "PhotoApp")
            if (photoFile != null && context != null) {
                val uriFile = FileUtil.getUriForFile(context!!, BuildConfig.APPLICATION_ID + ".provider", photoFile)
                cropPath = photoFile.absolutePath
                cropView.saveCroppedImageAsync(uriFile)



            }


        }
    }


    override fun initData(viewModel: CropViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        cropView.setImageUriAsync(Uri.fromFile(File(path)))
        viewModel.adapter.setOnItemClickListener(this)
    }

    override fun initViewModel(): Lazy<CropViewModel> {
        return viewModelByClass(CropViewModel::class) { parametersOf(path) }

    }

    override fun onItemClick(view: View, position: Int) {
        val cropViewModel = mBinding.viewModel
        cropViewModel?.adapter?.updateSelected(position)
        rcvRatio.layoutManager!!.scrollToPosition(position)
        val item = cropViewModel?.adapter?.getItem(position) ?: return
        when (item.type) {
            Ratio.RatioType.FREE -> cropView.setAspectRatio(1, 1)
            Ratio.RatioType.RATIO1_1 -> cropView.setAspectRatio(1, 1)
            Ratio.RatioType.RATIO1_2 -> cropView.setAspectRatio(1, 2)
            Ratio.RatioType.RATIO4_5 -> cropView.setAspectRatio(4, 5)
            Ratio.RatioType.RATIO5_4 -> cropView.setAspectRatio(5, 4)
            Ratio.RatioType.RATIO3_4 -> cropView.setAspectRatio(3, 4)
            Ratio.RatioType.RATIO4_3 -> cropView.setAspectRatio(4, 3)
            Ratio.RatioType.RATIO3_2 -> cropView.setAspectRatio(3, 2)
            Ratio.RatioType.RATIO2_3 -> cropView.setAspectRatio(2, 3)
            Ratio.RatioType.RATIO9_16 -> cropView.setAspectRatio(9, 16)
            Ratio.RatioType.RATIO16_9 -> cropView.setAspectRatio(16, 9)

        }
    }

    override fun onCropImageComplete(view: CropImageView?, result: CropImageView.CropResult?) {
        if (result != null && result.isSuccessful) {
            homeViewModel.addToMemoryCache(cropPath)
            activity!!.onBackPressed()
        } else {
            showToast(result!!.error.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as EditActivity).showEditorView(false)
    }
}