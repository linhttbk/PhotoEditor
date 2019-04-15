package com.linhtt.photoeditor.view.home

import android.Manifest
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.view.View
import com.example.core.BaseActivity
import com.example.core.permission.Permission
import com.example.core.permission.RxRequestPermission
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.photo.PhotoFragment
import com.sticker.Sticker
import com.sticker.StickerView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity :
    BaseActivity<HomeViewModel, com.linhtt.photoeditor.databinding.ActivityHomeBinding>(HomeViewModel::class),
    View.OnClickListener,StickerView.OnStickerOperationListener{
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

    override fun getLayoutResourceId(): Int = R.layout.activity_home
    private var disposable: Disposable? = null
    override fun getRoot(): Int = 0

    override fun initView() {
        super.initView()
        btnPhoto.setOnClickListener(this)
        btnCamera.setOnClickListener(this)
        btnDeviation.setOnClickListener(this)
        supportFragmentManager
    }

    override fun initData(viewModel: HomeViewModel) {
        super.initData(viewModel)
        binding.viewModel = viewModel
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onClick(v: View?) {
        when (v) {
            btnPhoto -> {
                requestReadStoragePermission()
            }
            btnCamera -> return
            btnDeviation -> return
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun requestReadStoragePermission() {
        disposable = RxRequestPermission(this).request(
            listOf(
                Permission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    false,
                    false
                ), Permission(Manifest.permission.READ_EXTERNAL_STORAGE, false, false)
            )
        )
            .subscribe { granted ->
                if (granted) {

                    switchFragment(PhotoFragment.newInstance(), true)
                } else {
                    val mSnackbar = Snackbar.make(
                        this.findViewById(R.id.root),
                        getString(R.string.msg_not_granted_permision),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    mSnackbar.setAction(getString(R.string.msg_close_snackbar)) {
                        mSnackbar.dismiss()
                    }
                    mSnackbar.show()
                }
            }


    }



    override fun onDestroy() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable?.dispose();
        }
        super.onDestroy()

    }
}