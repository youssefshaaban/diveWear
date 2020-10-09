package com.smartzone.diva_wear.ui.main.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.reponse.Request
import com.smartzone.diva_wear.databinding.ItemOrderBinding
import com.smartzone.diva_wear.utilis.AppUtils

class RequestsAdapter(
    val list: List<Request>
    , val clicReques: (Request) -> Unit
) :
    RecyclerView.Adapter<RequestsAdapter.SingleRow>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RequestsAdapter.SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_order, p0, false
            )
        )


    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: RequestsAdapter.SingleRow, p1: Int) {
        holder.bind(p1)
    }


    inner class SingleRow(var view: ItemOrderBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(pos: Int) {
            val request = list[pos]
            view.valueOrderNum.text = request.id
            view.tvPrice.text = "${request.price} ${view.root.context.getString(R.string.currency)}"
            view.tvName.text =
                view.root.context.getString(R.string.numberOfProduct) + "${request.products?.size}"
            request.Product?.let {
                AppUtils.loadGlideImage(
                    view.root.context,
                    it.image,
                    R.drawable.default_image,
                    view.imageProduct
                )
            }
            view.root.setOnClickListener {
                clicReques(request)
            }
        }
    }


}