package com.example.administrator.starkspireshopping

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.administrator.starkspire.model.ItemCategory
import com.example.administrator.starkspire.adapter.SparkAdapter
import com.example.administrator.starkspire.model.StarkSpireItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    lateinit var categoryRV: RecyclerView
    lateinit var itemCategory: List<ItemCategory>

    companion object {
        lateinit var instance: MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        categoryRV = findViewById(R.id.categoryRV)
        instance = this
        val gson = Gson()
        val queue = Volley.newRequestQueue(this)
        val url =   getString(R.string.url);

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    val starkSpireItem: StarkSpireItem = gson.fromJson(response, object: TypeToken<StarkSpireItem>() {}.type)
                    itemCategory = starkSpireItem.itemCategory!!
                    starkSpireItem.itemCategory?.let { setApplicationListViewType(it) }
                },
                Response.ErrorListener { println("That didn't work!") })
// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }


    internal fun setApplicationListViewType(itemCategory: List<ItemCategory>) {
        categoryRV.setLayoutManager(LinearLayoutManager(this))
        var sparkAdapter = SparkAdapter(this, itemCategory)
        categoryRV.setItemAnimator(DefaultItemAnimator())
        categoryRV.setAdapter(sparkAdapter)
    }
}
