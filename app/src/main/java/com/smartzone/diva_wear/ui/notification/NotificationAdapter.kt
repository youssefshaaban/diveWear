package com.smartzone.diva_wear.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Notification
import com.smartzone.diva_wear.databinding.ItemNotificationBinding
import com.smartzone.diva_wear.databinding.ItemProductBinding
import com.smartzone.diva_wear.utilis.AppUtils

class NotificationAdapter(
    val notifications: List<Notification>,
    val click: (Notification) -> Unit
) :
    RecyclerView.Adapter<NotificationAdapter.SingleRow>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): NotificationAdapter.SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_notification, p0, false
            )
        )


    }

    override fun getItemCount(): Int {
        return notifications.size
    }


    override fun onBindViewHolder(holder: NotificationAdapter.SingleRow, p1: Int) {
        holder.bind(p1)
    }


    inner class SingleRow(var view: ItemNotificationBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(pos: Int) {
            val notification = notifications[pos]
            view.tittle.text=notification.title
            view.date.text=notification.date
            view.root.setOnClickListener {
                click(notification)
            }
        }
    }


}