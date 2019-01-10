package com.example.administrator.starkspire.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.administrator.starkspire.model.ItemCategory
import com.example.administrator.starkspireshopping.Database.DbTable
import com.example.administrator.starkspireshopping.Main2Activity
import com.example.administrator.starkspireshopping.R

class SparkAdapter(internal var context: Context, internal var itemCategoriesList: List<ItemCategory>,internal var dbTable: DbTable) : RecyclerView.Adapter<SparkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val layoutresource = R.layout.cat_item
        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(layoutresource, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.titleTV.text = itemCategoriesList[i].name
        val childAgeList = itemCategoriesList[i].childCategories
        var age:String =""
        if (childAgeList != null) {
            for (childage in childAgeList){
                age +=childage
            }
        }
        if(!age.isEmpty()) {
            viewHolder.childCatTV.text = "Child age: ${age}"
        }else{
            viewHolder.childCatTV.text=""
        }

        viewHolder.itemView.setOnClickListener{
            val intent = Intent(context, Main2Activity::class.java)
            intent.putExtra(context.getString(R.string.cat_id), itemCategoriesList[i].id)
            intent.putExtra(context.getString(R.string.cat_name), itemCategoriesList[i].name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemCategoriesList.size
    }

    inner class ViewHolder internal constructor(convertView: View) : RecyclerView.ViewHolder(convertView) {
        internal var titleTV: TextView
        internal var childCatTV: TextView
        init {
            titleTV = convertView.findViewById(R.id.titleTV)
            childCatTV = convertView.findViewById(R.id.childCatTV)
        }
    }
}
