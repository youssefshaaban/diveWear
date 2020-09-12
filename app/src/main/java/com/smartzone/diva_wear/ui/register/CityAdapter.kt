package com.smartzone.diva_wear.ui.register

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.City

open class CityAdapter(context: Context, val resource: Int, val list: List<City>) :
    ArrayAdapter<City>(context, resource, list) {
    var vi: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getItem(position: Int): City? {
        return list.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val city=list.get(position)
        var holder: ViewHolder
        var retView: View
        if(convertView == null){
            retView = vi.inflate(resource, null)
            holder = ViewHolder()

            holder.txt = retView.findViewById(android.R.id.text1) as TextView?
            holder.txt?.setText(city.name)
            retView.tag = holder

        } else {
            holder = convertView.tag as ViewHolder
            retView = convertView
        }
        return retView
    }

    internal class ViewHolder {
        var txt: TextView? = null
    }
}