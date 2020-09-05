package com.smartzone.diva_wear.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Category
import com.smartzone.diva_wear.databinding.ItemCategoryViewBinding
import com.smartzone.diva_wear.utilis.AppUtils

class CategoryAdapter(
    val categories: List<Category>,
    val click: (Category) -> Unit

) :
    RecyclerView.Adapter<CategoryAdapter.SingleRow>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): CategoryAdapter.SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_category_view, p0, false
            )
        )


    }

    override fun getItemCount(): Int {
        return categories.size
    }


    override fun onBindViewHolder(holder: CategoryAdapter.SingleRow, p1: Int) {
        holder.bind(p1)
    }


    inner class SingleRow(var view: ItemCategoryViewBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(pos: Int) {
            val category = categories[pos]
            view.tittle.text=category.name
            AppUtils.loadGlideImage(view.root.context,category.image,R.drawable.default_image,view.categoryImage)
            view.root.setOnClickListener {
                click(category)
            }

        }
    }


}