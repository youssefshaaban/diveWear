package com.smartzone.diva_wear.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Slider
import com.smartzone.diva_wear.databinding.ItemImageSliderBinding
import com.smartzone.diva_wear.utilis.AppUtils

class SlidderAdapter(
    val categories: List<Slider>,
    val click: (Slider) -> Unit

) :
    RecyclerView.Adapter<SlidderAdapter.SingleRow>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): SlidderAdapter.SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_image_slider, p0, false
            )
        )


    }

    override fun getItemCount(): Int {
        return categories.size
    }


    override fun onBindViewHolder(holder: SlidderAdapter.SingleRow, p1: Int) {
        holder.bind(p1)
    }


    inner class SingleRow(var view: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(pos: Int) {
            val category = categories[pos]
            AppUtils.loadGlideImage(view.root.context,category.image,R.drawable.default_image,view.image)
            view.root.setOnClickListener {
                click(category)
            }

        }
    }


}