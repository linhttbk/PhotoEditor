package com.linhtt.photoeditor.view.draw

import android.support.v4.view.ViewPager
import com.example.core.BaseFragment
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.editor.EditActivity
import kotlinx.android.synthetic.main.fragment_draw.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class DrawFragment :
    BaseFragment<DrawViewModel, com.linhtt.photoeditor.databinding.FragmentDrawBinding>(DrawViewModel::class) {

    companion object {
        fun newInstance(): DrawFragment = DrawFragment()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_draw
    }

    override fun initView() {
        super.initView()

        imvApply.setOnClickListener {
            activity?.onBackPressed()
        }
        imvClose.setOnClickListener {
            activity?.onBackPressed()
        }
        imvDraw.setImageResource(R.drawable.icon_draw_size_active)
        imvColor.setOnClickListener {
            vpDraw.currentItem = 1
            imvColor.setImageResource(R.drawable.icon_color_active)
            imvDraw.setImageResource(R.drawable.icon_draw_size)
        }
        imvDraw.setOnClickListener {
            vpDraw.currentItem = 0
            imvColor.setImageResource(R.drawable.icon_color)
            imvDraw.setImageResource(R.drawable.icon_draw_size_active)
        }
        vpDraw.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                if(p0 ==0){
                    imvColor.setImageResource(R.drawable.icon_color)
                    imvDraw.setImageResource(R.drawable.icon_draw_size_active)
                }else{
                    imvColor.setImageResource(R.drawable.icon_color_active)
                    imvDraw.setImageResource(R.drawable.icon_draw_size)
                }
            }

            override fun onPageScrollStateChanged(p0: Int) {
            }

        })
    }

    override fun initData(viewModel: DrawViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
    }

    override fun initViewModel(): Lazy<DrawViewModel> {
        return viewModelByClass(DrawViewModel::class) { parametersOf(childFragmentManager) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as EditActivity).showEditorView(false)
    }
}