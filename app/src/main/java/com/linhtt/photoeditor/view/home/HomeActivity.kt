package com.linhtt.photoeditor.view.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.content.FileProvider
import android.view.View
import com.example.core.BaseActivity
import com.example.core.permission.Permission
import com.example.core.permission.RxRequestPermission
import com.linhtt.photoeditor.BuildConfig
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.editor.EditActivity
import com.linhtt.photoeditor.view.photo.PhotoFragment
import com.sticker.Sticker
import com.sticker.StickerView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import java.io.File


class HomeActivity :
    BaseActivity<HomeViewModel, com.linhtt.photoeditor.databinding.ActivityHomeBinding>(HomeViewModel::class),
    View.OnClickListener, StickerView.OnStickerOperationListener {
    override fun onClickEdit(sticker: Sticker) {

    }

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
    private var compositeDisposable = CompositeDisposable()
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
            btnCamera -> requestCameraPermission()
            btnDeviation -> return
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun requestReadStoragePermission() {
        val disposable = RxRequestPermission(this).request(
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
        compositeDisposable.add(disposable)

    }

    private fun requestCameraPermission() {
        val disposable = RxRequestPermission(this).request(
            listOf(
                Permission(
                    Manifest.permission.CAMERA,
                    false,
                    false
                )
            )
        )
            .subscribe { granted ->
                if (granted) {
                    getImageFromCamera()

                } else {
                    val mSnackbar = Snackbar.make(
                        this.findViewById(R.id.root),
                        getString(R.string.msg_not_granted_permision_camera),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    mSnackbar.setAction(getString(R.string.msg_close_snackbar)) {
                        mSnackbar.dismiss()
                    }
                    mSnackbar.show()
                }
            }
        compositeDisposable.add(disposable)
    }

    private var mImagePath: String = ""

    private fun getImageFromCamera() {
        val photoDirectory = File(Environment.getExternalStorageDirectory(), "PhotoApp")
        if (!photoDirectory.exists() && !photoDirectory.mkdirs()) {
            return
        }
        val photo = File(photoDirectory, System.currentTimeMillis().toString() + ".png")

        mImagePath = photo.path
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val mPhotoUri = FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID + ".provider",
            photo
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri)
        startActivityForResult(intent, 90)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 90){
            if (resultCode == Activity.RESULT_OK){
                val intent = Intent(this, EditActivity::class.java)
                val bundle = Bundle()
                bundle.putString("path", mImagePath)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()

    }
}