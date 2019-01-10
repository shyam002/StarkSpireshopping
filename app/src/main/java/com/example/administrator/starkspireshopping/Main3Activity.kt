package com.example.administrator.starkspireshopping

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.administrator.starkspire.model.Variants
import com.example.administrator.starkspireshopping.Database.DbTable
import com.example.administrator.starkspireshopping.adapter.VariantAdapter

class Main3Activity : AppCompatActivity() {

    lateinit var variantRV: RecyclerView
    lateinit var RankTV:TextView
    lateinit var dbTable: DbTable
    lateinit var variantList: List<Variants>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        init()
        val productId = intent.getStringExtra(getString(R.string.productid))
        val productname = intent.getStringExtra(getString(R.string.productName))
        if (productId != null) {
            val rank =dbTable.getRankOfProduct(productId)
            RankTV.text = rank
            variantList = dbTable.getVariantList(productId)
            this.title = productname
            if (variantList.size > 0) {
                setApplicationListViewType(variantList)
            }
        }
    }

    internal fun init() {
        RankTV = findViewById(R.id.RankTV)
        variantRV = findViewById(R.id.variantRV)
        dbTable = DbTable(getApplicationContext())
    }

    internal fun setApplicationListViewType(variantList: List<Variants>) {
        variantRV.setLayoutManager(LinearLayoutManager(this))
        var itemAdapter = VariantAdapter(this, variantList)
        variantRV.setItemAnimator(DefaultItemAnimator())
        variantRV.setAdapter(itemAdapter)
    }
}
