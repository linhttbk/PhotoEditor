package com.linhtt.photoeditor.view.photo

import android.arch.lifecycle.Observer
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.example.core.custom.VerticalSpacesItemDecoration
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.crop.CropFragment
import com.linhtt.photoeditor.view.editor.PhotoEditorFragment
import kotlinx.android.synthetic.main.fragment_photo.*


class PhotoFragment :
    BaseFragment<PhotoViewModel, com.linhtt.photoeditor.databinding.FragmentPhotoBinding>(PhotoViewModel::class),
    View.OnClickListener {


    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_photo
    }

    companion object {
        fun newInstance(): PhotoFragment = PhotoFragment()
    }

    override fun initView() {
        super.initView()
        rcvList.layoutManager = GridLayoutManager(context, 4)
        rcvList.addItemDecoration(VerticalSpacesItemDecoration(5, true, 4))
        rcvFolder.layoutManager = LinearLayoutManager(context)
        imvBack.setOnClickListener(this)
        tvFolder.setOnClickListener(this)

    }

    override fun initViewModel(): Lazy<PhotoViewModel> {
        return super.initViewModel()
    }

    override fun initData(viewModel: PhotoViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        viewModel.positionSelected.observe(this, Observer<String> { t ->
            tvFolder.text = t
            hideFolderList()

        })
        viewModel.photoAdapter.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val path = viewModel.photoAdapter.getItem(position)
                switchFragment(PhotoEditorFragment.newInstance(path), true)
            }
        })


    }


    override fun onClick(v: View?) {
        when (v) {
            imvBack -> activity!!.onBackPressed()
            tvFolder -> {
                if (rcvFolder.visibility == View.GONE) {

                    showFolderList()


                } else {
                    android.os.Handler().post{
                        hideFolderList()
                    }

                }
            }
        }
    }

    private fun showFolderList() {
        val animate = AnimationUtils.loadAnimation(context, R.anim.slide_down_from_top)
        rcvFolder!!.startAnimation(animate)
        rcvFolder.visibility = View.VISIBLE
    }

    private fun hideFolderList() {
        val animate = AnimationUtils.loadAnimation(context, R.anim.slide_up_from_bottom)
        rcvFolder!!.startAnimation(animate)
        rcvFolder.visibility = View.GONE
    }
}