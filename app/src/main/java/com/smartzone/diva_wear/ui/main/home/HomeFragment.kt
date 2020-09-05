package com.smartzone.diva_wear.ui.main.home

import android.icu.util.UniversalTimeScale.MAX_SCALE
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayoutMediator
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Slider
import com.smartzone.diva_wear.databinding.FragmentHomeBinding
import com.smartzone.diva_wear.ui.base.BaseFragment
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.products.ProductsActivity
import com.smartzone.diva_wear.utilis.CATEGORY_NAME
import com.smartzone.diva_wear.utilis.ID_KEY
import com.smartzone.diva_wear.utilis.ViewUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    lateinit var binding: FragmentHomeBinding
    private val viewModel:HomeViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=getViewDataBinding()
//        binding.pager.clipToPadding = false
//        binding.pager.pageMargin = ViewUtils.dpToPx(10.0f)

        //binding.pager.setPageTransformer(false,transformer)
        binding.recycleCategories.layoutManager=GridLayoutManager(activity,2)
        viewModel.loadData()
        binding.contentSlider.visibility=View.INVISIBLE
        //viewModel.getSlider()
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    fun observeSlider(){
        viewModel.slider.observe(viewLifecycleOwner, Observer {
            sliders->
            activity?.let {
                binding.pager.adapter=SlidderAdapter(sliders as ArrayList<Slider>){

                }
                TabLayoutMediator(binding.tabLayoutDots, binding.pager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
                }.attach()
            }
        })
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeCategories()
        observeSlider()
    }
    fun observeCategories(){
        viewModel.categories.observe(viewLifecycleOwner, Observer {
            binding.recycleCategories.adapter=CategoryAdapter(it){
                startActivity(ProductsActivity.getIntent(binding.getRoot().context).apply {
                    putExtra(ID_KEY,it.id)
                    putExtra(CATEGORY_NAME,it.name)
                })
            }
        })
    }
    private var transformer: ViewPager.PageTransformer =
        ViewPager.PageTransformer { page, position ->
            val pagerWidthPx = (page.parent as ViewPager).width.toFloat()
            val pageWidthPx: Float = pagerWidthPx - 2 * ViewUtils.dpToPx(5f)
            val maxVisiblePages = pagerWidthPx / pageWidthPx
            val center = maxVisiblePages / 2f
            val scale: Float
            if (position + 0.5f < center - 0.5f || position > center) {
                scale = 0.8f
            } else {
                val coef: Float = if (position + 0.5f < center) {
                    (position + 1 - center) / 0.5f
                } else {
                    (center - position) / 0.5f
                }
                scale = coef * (MAX_SCALE - 0.8f +  0.8f)
            }
            page.scaleX = scale
            page.scaleY = scale
        }
}