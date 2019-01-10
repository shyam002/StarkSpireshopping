package com.example.administrator.starkspireshopping

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.administrator.starkspire.adapter.ItemAdapter
import com.example.administrator.starkspire.model.Product
import com.example.administrator.starkspireshopping.Database.DbTable

class Main2Activity : AppCompatActivity() {
    lateinit var itemRV: RecyclerView
    lateinit var noprodTV:TextView
    lateinit var productlist: List<Product>
    lateinit var dbTable: DbTable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        init()
        val catId = intent.getStringExtra(getString(R.string.cat_id))
        val catname = intent.getStringExtra(getString(R.string.cat_name))

        if (catId != null) {
            productlist = dbTable.productlist(catId.toString())
            this.title = catname
            if (productlist.size>0) {
                noprodTV.visibility = View.GONE
                setApplicationListViewType(productlist)
            }else{
                itemRV.visibility = View.GONE
            }
        }
    }

    internal fun init() {
        itemRV = findViewById(R.id.itemRV)
        noprodTV = findViewById(R.id.noprodTV)
        dbTable = DbTable(getApplicationContext())

    }

    internal fun setApplicationListViewType(productlist: List<Product>) {
        itemRV.setLayoutManager(LinearLayoutManager(this))
        var itemAdapter = ItemAdapter(this, productlist,dbTable)
        itemRV.setItemAnimator(DefaultItemAnimator())
        itemRV.setAdapter(itemAdapter)
    }
}
