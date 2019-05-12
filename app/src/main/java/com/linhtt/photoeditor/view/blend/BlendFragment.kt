package com.linhtt.photoeditor.view.blend

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.example.core.custom.VerticalSpacesItemDecoration
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.editor.EditActivity
import com.linhtt.photoeditor.view.editor.EditorViewModel
import kotlinx.android.synthetic.main.dialog_pick_image.view.*
import kotlinx.android.synthetic.main.fragment_blend.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class BlendFragment :
    BaseFragment<BlendViewModel, com.linhtt.photoeditor.databinding.FragmentBlendBinding>(BlendViewModel::class) {
    var path: String = ""
    var bottomSheetDialog: BottomSheetDialog? = null
    val shareViewModel: EditorViewModel by sharedViewModel()

    companion object {
        fun newInstance(path: String): BlendFragment {
            val blendFragment = BlendFragment()
            blendFragment.path = path
            return blendFragment
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_blend
    }

    override fun initView() {
        super.initView()
        rcvBlend.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun initData(viewModel: BlendViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        viewModel.adapter.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                if (position == 0) {
                    showPickImage()
                } else {
                    viewModel.adapter.updateSelected(position)
                    val item = viewModel.adapter.getItem(position)
                    if (item.filter != null) {
                        shareViewModel.addBlendFilter(item.filter, currentPath = viewModel.adapter.bendPath,
                            adjust = (activity as EditActivity).applyFilterGroup(shareViewModel.getFilterGroup())
                        )
                        (activity as EditActivity).applyAdjust()

                    }

                }
            }
        })
    }

    override fun initViewModel(): Lazy<BlendViewModel> {
        return viewModelByClass(BlendViewModel::class) { parametersOf(path) }
    }

    fun showPickImage() {
        if (bottomSheetDialog == null) {
            bottomSheetDialog = BottomSheetDialog(context!!)
            val binding: com.linhtt.photoeditor.databinding.DialogPickImageBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.dialog_pick_image, null, false)
            bottomSheetDialog?.setContentView(binding.root)
            binding.viewModel = mBinding.viewModel

            binding.root.rcvImage.layoutManager = GridLayoutManager(context, 4)
            binding.root.rcvImage.addItemDecoration(VerticalSpacesItemDecoration(5, true, 4))
            binding.root.rcvFolder.layoutManager = LinearLayoutManager(context)
            binding.viewModel?.positionSelected?.observe(this, Observer<String> { t ->
                binding.root.tvFolder.text = t
                hideFolderList(binding.root.rcvFolder)

            })
            binding.root.tvFolder.setOnClickListener {
                if (binding.root.rcvFolder.visibility == View.GONE) {

                    showFolderList(binding.root.rcvFolder)


                } else {
                    android.os.Handler().post {
                        hideFolderList(binding.root.rcvFolder)
                    }

                }
            }
            binding.viewModel?.photoAdapter?.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val path = binding.viewModel?.photoAdapter?.getItem(position)
                    if (!path.isNullOrEmpty()) {
                        binding.viewModel?.changePathBlend(path)
                        bottomSheetDialog?.dismiss()
                    }

                }
            })
            binding.viewModel?.folderAdapter?.setOnItemClickListener(object : BaseRecycleViewAdapter.ItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val photo = binding.viewModel?.folderAdapter?.getItem(position)
                    if (photo != null) {
                        binding.viewModel?.positionSelected?.value = photo.folder
                        binding.viewModel?.photoAdapter?.replace(photo.path)
                    }
                }
            })

        }
        bottomSheetDialog?.show()
        mBinding.viewModel?.loadPhotoAndFolder(context!!)

    }

    private fun showFolderList(rcvFolder: RecyclerView) {
        val animate = AnimationUtils.loadAnimation(context, R.anim.slide_down_from_top)
        rcvFolder.startAnimation(animate)
        rcvFolder.visibility = View.VISIBLE
        animate.cancel()
    }

    private fun hideFolderList(rcvFolder: RecyclerView) {
        val animate = AnimationUtils.loadAnimation(context, R.anim.slide_up_from_bottom)
        rcvFolder.startAnimation(animate)
        rcvFolder.visibility = View.GONE
        animate.cancel()
    }
}