package com.example.administrator.starkspire.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.administrator.starkspire.model.ItemCategory
import com.example.administrator.starkspireshopping.Main2Activity
import com.example.administrator.starkspireshopping.R

class SparkAdapter(internal var context: Context, internal var itemCategoriesList: List<ItemCategory>) : RecyclerView.Adapter<SparkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val layoutresource = R.layout.cat_item
        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(layoutresource, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.titleTV.text = itemCategoriesList[i].name
        viewHolder.titleTV.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Main2Activity::class.java)
            intent.putExtra(context.getString(R.string.cat_position), i)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return itemCategoriesList.size
    }

    inner class ViewHolder internal constructor(convertView: View) : RecyclerView.ViewHolder(convertView) {
        internal var titleTV: TextView

        init {
            titleTV = convertView.findViewById(R.id.titleTV)
        }
    }
}
