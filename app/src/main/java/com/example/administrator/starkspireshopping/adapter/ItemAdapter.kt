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
import com.example.administrator.starkspireshopping.Main3Activity
import com.example.administrator.starkspireshopping.R

class ItemAdapter(internal var context: Context, internal var itemList: List<Product>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val layoutresource = R.layout.items_details
        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(layoutresource, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.titleTV.text = itemList[i].name
        viewHolder.dateTV.text = itemList[i].date_added
        viewHolder.relativelayout.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Main3Activity::class.java)
            intent.putExtra(context.getString(R.string.item_position), i)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder internal constructor(convertView: View) : RecyclerView.ViewHolder(convertView) {
        internal lateinit var titleTV: TextView
        internal lateinit var dateTV: TextView
        internal lateinit var relativelayout: RelativeLayout

        init {
            titleTV = convertView.findViewById(R.id.titleTV)
            dateTV = convertView.findViewById(R.id.dateTV)
            relativelayout = convertView.findViewById(R.id.relativelayout)
        }
    }
}
