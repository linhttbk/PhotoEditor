package com.linhtt.photoeditor.view.draw.page

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.core.BaseFragment
import com.example.core.BaseRecycleViewAdapter
import com.linhtt.photoeditor.R
import com.linhtt.photoeditor.view.draw.DrawViewModel
import com.linhtt.photoeditor.view.editor.EditActivity
import kotlinx.android.synthetic.main.fragment_color_draw.*
import org.koin.android.viewmodel.ext.android.viewModelByClass
import org.koin.core.parameter.parametersOf

class ColorDrawFragment:BaseFragment<DrawViewModel,com.linhtt.photoeditor.databinding.FragmentColorDrawBinding>(DrawViewModel::class){
    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_color_draw
    }

    override fun initView() {
        super.initView()
        rcvColorDraw.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

    }

    override fun initData(viewModel: DrawViewModel) {
        super.initData(viewModel)
        mBinding.viewModel = viewModel
        viewModel.colorAdapter.setOnItemClickListener(object :BaseRecycleViewAdapter.ItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val item = viewModel.colorAdapter.getItem(position)
                val resID = resources.getIdentifier(
                    "color" + item.color,
                    "color", context?.packageName
                )
                (activity as EditActivity).changeDrawColor(ContextCompat.getColor(context!!, resID))
            }

        })
    }
    override fun initViewModel(): Lazy<DrawViewModel> {
        return viewModelByClass(DrawViewModel::class) { parametersOf(childFragmentManager) }
    }

    companion object {
        fun newInstance():ColorDrawFragment = ColorDrawFragment()
    }
}