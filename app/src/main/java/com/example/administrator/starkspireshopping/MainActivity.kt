package com.example.administrator.starkspireshopping

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.Log.v
import android.view.View
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.administrator.starkspire.adapter.SparkAdapter
import com.example.administrator.starkspire.model.ItemCategory
import com.example.administrator.starkspire.model.Ranking
import com.example.administrator.starkspire.model.StarkSpireItem
import com.example.administrator.starkspireshopping.Database.DbTable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    lateinit var categoryRV: RecyclerView
    lateinit var itemCategory: List<ItemCategory>
    lateinit var ranking: List<Ranking>
    lateinit var dbTable: DbTable
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        getDataFromServer()
    }

    internal fun init(){
        categoryRV = findViewById(R.id.categoryRV)
        dbTable = DbTable(getApplicationContext())
        progressBar = findViewById(R.id.progressbar)
    }
    internal fun getDataFromServer(){
        val gson = Gson()
        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.url);
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    val starkSpireItem: StarkSpireItem = gson.fromJson(response, object : TypeToken<StarkSpireItem>() {}.type)
                    itemCategory = starkSpireItem.itemCategory!!
                    if (itemCategory != null) {
                        dbTable.deleteWholeDatabase()
                        for (item in itemCategory) {
                            //inserting data to category table
                            dbTable.insertCategory(item)
                            if (item.childCategories != null) {
                                for (i in item.childCategories!!) {
                                    dbTable.insertChildCategories(item.id!!, i.toString())
                                }
                            }
                            if (item.products != null) {
                                for (product in item.products!!) {
                                    //inserting data to Products
                                    dbTable.insertPRODUCTS(item.id!!, product)
                                    //insert tax data
                                    dbTable.insertTAX(product.id!!, product.tax!!)
                                    //inserting data to variants
                                    if (product.variants != null) {
                                        for (variant in product.variants!!) {
                                            dbTable.insertVariantData(variant, product.id!!)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (starkSpireItem.ranking != null) {
                        ranking = starkSpireItem.ranking!!
                        for (rnking in ranking) {
                            if (rnking.rankingProducts != null) {
                                for (rankingproduct in rnking.rankingProducts!!) {
                                    dbTable.insertRanking(rnking.ranking!!, rankingproduct)
                                }
                            }
                        }
                    }
                    //showing list of data
                    itemCategory?.let { setApplicationListViewType(it) }
                },
                Response.ErrorListener { setCatDataFromDB() })
// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    internal fun setCatDataFromDB() {
        v("MainActivity", "setCatDataFromDB call")
        itemCategory = dbTable.categories
        if (itemCategory != null) {
            setApplicationListViewType(itemCategory)
        }

    }

    internal fun setApplicationListViewType(itemCategory: List<ItemCategory>) {
        progressBar.visibility = View.GONE
        categoryRV.visibility = View.VISIBLE
        categoryRV.setLayoutManager(LinearLayoutManager(this))
        var sparkAdapter = SparkAdapter(this, itemCategory,dbTable)
        categoryRV.setItemAnimator(DefaultItemAnimator())
        categoryRV.setAdapter(sparkAdapter)
    }
}
