package com.example.administrator.starkspire.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.administrator.starkspire.model.Product
import com.example.administrator.starkspireshopping.Database.DbTable
import com.example.administrator.starkspireshopping.Main3Activity
import com.example.administrator.starkspireshopping.R

class ItemAdapter(internal var context: Context, internal var itemList: List<Product>,internal var dbTable: DbTable) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val layoutresource = R.layout.items_details
        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(layoutresource, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.titleTV.text = itemList[i].name
        viewHolder.dateTV.text = itemList[i].date_added
        val tax = dbTable.getTaxData(itemList[i].id!!)
        viewHolder.taxNameTV.text ="${tax.name}: "
        viewHolder.taxvalueTV.text = tax.value
        val count = dbTable.getVariantsCount(itemList[i].id!!)
        if (count>0) {
            viewHolder.variantCountTV.text = "$count Variants available"
        }else{
            viewHolder.variantCountTV.text =""
        }
        viewHolder.itemView.setOnClickListener {
            if(count>0) {
                val intent = Intent(context, Main3Activity::class.java)
                intent.putExtra(context.getString(R.string.productid), itemList[i].id)
                intent.putExtra(context.getString(R.string.productName), itemList[i].name)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder internal constructor(convertView: View) : RecyclerView.ViewHolder(convertView) {
        internal var titleTV: TextView
        internal var dateTV: TextView
        internal var taxNameTV: TextView
        internal var taxvalueTV: TextView
        internal var variantCountTV: TextView


        init {
            titleTV = convertView.findViewById(R.id.titleTV)
            dateTV = convertView.findViewById(R.id.dateTV)
            taxNameTV = convertView.findViewById(R.id.taxNameTV)
            taxvalueTV = convertView.findViewById(R.id.taxvalueTV)
            variantCountTV = convertView.findViewById(R.id.variantCountTV)
        }
    }
}
