package com.example.administrator.starkspireshopping.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.administrator.starkspire.model.Variants
import com.example.administrator.starkspireshopping.R

class VariantAdapter(internal var context: Context, internal var itemList: List<Variants>) : RecyclerView.Adapter<VariantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val layoutresource = R.layout.variant_item
        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(layoutresource, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.colorTV.text = itemList[i].color
        viewHolder.sizeTV.text = itemList[i].size
        viewHolder.priceTV.text = itemList[i].price
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder internal constructor(convertView: View) : RecyclerView.ViewHolder(convertView) {
        internal var colorTV: TextView
        internal var sizeTV: TextView
        internal var priceTV: TextView


        init {
            colorTV = convertView.findViewById(R.id.colorTV)
            sizeTV = convertView.findViewById(R.id.sizeTV)
            priceTV = convertView.findViewById(R.id.priceTV)
        }
    }
}