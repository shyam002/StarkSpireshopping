package com.example.administrator.starkspireshopping

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.administrator.starkspire.adapter.ItemAdapter
import com.example.administrator.starkspire.model.ItemCategory
import com.example.administrator.starkspire.model.Product

class Main2Activity : AppCompatActivity() {
    lateinit var itemRV: RecyclerView
    lateinit var itemCategory: List<ItemCategory>
    lateinit var itemCat: ItemCategory
    lateinit var productlist: List<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        itemRV = findViewById(R.id.itemRV)
        val position = intent.getIntExtra(getString(R.string.cat_position), -1)
        val instance = MainActivity.Companion.instance
        if (instance != null) {
            itemCategory = instance.itemCategory
            if (itemCategory != null) {
                if (position != -1) {
                    itemCat = itemCategory.get(position)
                    productlist = itemCat.products!!
                    val name = itemCat.name
                    this.title = name
                    setApplicationListViewType(productlist)
                }
            }
        }
    }

    internal fun setApplicationListViewType(productlist: List<Product>) {
        itemRV.setLayoutManager(GridLayoutManager(this, 2))
        var itemAdapter = ItemAdapter(this, productlist)
        itemRV.setItemAnimator(DefaultItemAnimator())
        itemRV.setAdapter(itemAdapter)
    }
}
