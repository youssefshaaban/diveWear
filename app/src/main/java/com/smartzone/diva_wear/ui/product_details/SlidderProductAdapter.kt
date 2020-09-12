package com.smartzone.diva_wear.ui.product_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.databinding.ItemImageSliderBinding
import com.smartzone.diva_wear.utilis.AppUtils

class SlidderProductAdapter(
    val pathes: List<String>

) :
    RecyclerView.Adapter<SlidderProductAdapter.SingleRow>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): SlidderProductAdapter.SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_image_slider, p0, false
            )
        )


    }

    override fun getItemCount(): Int {
        return pathes.size
    }


    override fun onBindViewHolder(holder: SlidderProductAdapter.SingleRow, p1: Int) {
        holder.bind(p1)
    }


    inner class SingleRow(var view: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(pos: Int) {
            val category = pathes[pos]
            AppUtils.loadGlideImage(view.root.context,category,R.drawable.default_image,view.image)


        }
    }


}