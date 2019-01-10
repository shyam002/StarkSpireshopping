package com.example.administrator.starkspireshopping.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.administrator.starkspire.model.*

import java.util.ArrayList

class DbTable(context: Context) {


    var dbHelper: DbHelper

    //get Categories details NAME and ID from TABLE_CATEGORY
    //            db.beginTransaction();
    //            db.setTransactionSuccessful();
    //            db.endTransaction();
    val categories: List<ItemCategory>
        @Synchronized get() {
            var db: SQLiteDatabase? = null
            var c: Cursor? = null
            val itemCategoriesList = ArrayList<ItemCategory>()
            try {
                db = dbHelper.readableDatabase
                c = db!!.query(TABLE_CATEGORY, null, null, null, null, null, null)
                if (c!!.moveToFirst()) {
                    do {
                        val itemCategory = ItemCategory()
                        itemCategory.id = c.getString(c.getColumnIndex(KEY_CATEGORY_ID))
                        itemCategory.name = c.getString(c.getColumnIndex(KEY_CATEGORY_NAME))
                        itemCategory.childCategories = getChildCat(c.getString(c.getColumnIndex(KEY_CATEGORY_ID)))
                        itemCategoriesList.add(itemCategory)
                    } while (c.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    if (c != null) {
                        c.close()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            return itemCategoriesList
        }
    @Synchronized
    fun productlist(catId:String): List<Product> {
            var db: SQLiteDatabase? = null
            var c: Cursor? = null
            val productlist = ArrayList<Product>()
            try {
                db = dbHelper.readableDatabase
                c = db!!.query(TABLE_PRODUCT, null,KEY_CATEGORY_ID + "=?", arrayOf(catId), null, null, null)
                if (c!!.moveToFirst()) {
                    do {
                        val product = Product()
                        product.id = c.getString(c.getColumnIndex(KEY_PRODUCT_ID))
                        product.name = c.getString(c.getColumnIndex(KEY_PRODUCT_NAME))
                        product.date_added = c.getString(c.getColumnIndex(KEY_PRODUCT_DATE))
                        productlist.add(product)
                    } while (c.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    if (c != null) {
                        c.close()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            return productlist
        }
    @Synchronized
    fun getTaxData(productid:String): Tax {
        var db: SQLiteDatabase? = null
        var c: Cursor? = null
        val tax = Tax()
        try {
            db = dbHelper.readableDatabase
            c = db!!.query(TABLE_TAX, null, KEY_PRODUCT_ID + "=?", arrayOf(productid), null, null, null)
            if (c!!.moveToFirst()) {
                    tax.value = c.getString(c.getColumnIndex(KEY_TAX_VALUE))
                    tax.name = c.getString(c.getColumnIndex(KEY_TAX_NAME))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (c != null) {
                    c.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return tax
    }

    // Getting Variants Count
    @Synchronized
    fun getVariantsCount(productid: String): Int {
        var db: SQLiteDatabase? = null
        var cursor: Cursor? = null
        var count = 0
        try {
            val countQuery = "SELECT  * FROM $TABLE_VARIANT where $KEY_PRODUCT_ID == $productid"
            db = dbHelper.readableDatabase
            cursor = db!!.rawQuery(countQuery, null)
            count = cursor!!.count
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (cursor != null) {
                    cursor.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return count
    }

    @Synchronized
    fun deleteWholeDatabase(){
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db!!.beginTransaction()
            db.delete(TABLE_VARIANT, null, null)
            db.delete(TABLE_CATEGORY, null, null)
            db.delete(TABLE_CHILD_CATEGORIES, null, null)
            db.delete(TABLE_PRODUCT, null, null)
            db.delete(TABLE_RANKING, null, null)
            db.delete(TABLE_TAX, null, null)
            db.setTransactionSuccessful()
            db.endTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @Synchronized
    fun getVariantList(productId:String): List<Variants> {
        var db: SQLiteDatabase? = null
        var c: Cursor? = null
        val variantList = ArrayList<Variants>()
        try {
            db = dbHelper.readableDatabase
            c = db!!.query(TABLE_VARIANT, null, KEY_PRODUCT_ID + "=?", arrayOf(productId), null, null, null)
            if (c!!.moveToFirst()) {
                do {
                    val variant = Variants()
                    variant.id = c.getString(c.getColumnIndex(KEY_VARIANT_ID))
                    variant.color = c.getString(c.getColumnIndex(KEY_VARIANT_COLOR))
                    variant.size = c.getString(c.getColumnIndex(KEY_VARIANT_SIZE))
                    variant.price = c.getString(c.getColumnIndex(KEY_VARIANT_PRICE))
                    variantList.add(variant)
                } while (c.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (c != null) {
                    c.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return variantList
    }
    @Synchronized
    fun getRankOfProduct(productId:String): String {
        var db: SQLiteDatabase? = null
        var c: Cursor? = null
        var rank:String=""
        try {
            Log.v("DbTable",productId)
            db = dbHelper.readableDatabase
            c = db!!.query(TABLE_RANKING, null, KEY_PRODUCT_ID + "=?", arrayOf(productId), null, null, null)
            if (c!!.moveToFirst()) {
                do {
                    val count = c.getString(c.getColumnIndex(KEY_RANKING_VIEW_COUNT))
                    val type = c.getString(c.getColumnIndex(KEY_RANKING_TYPE))
                    if(!count.equals("0")) {
                        rank += "$type : $count \n"
                    }
                    Log.v("DbTable","type: $type")
                    Log.v("DbTable","count: $count")
                } while (c.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (c != null) {
                    c.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return rank
    }
    @Synchronized
    fun getChildCat(catId:String): List<Int> {
        var db: SQLiteDatabase? = null
        var c: Cursor? = null
        val childageList = ArrayList<Int>()
        try {
            db = dbHelper.readableDatabase
            c = db!!.query(TABLE_CHILD_CATEGORIES, null, KEY_CATEGORY_ID + "=?", arrayOf(catId), null, null, null)
            if (c!!.moveToFirst()) {
                do {
                    childageList.add((c.getString(c.getColumnIndex(KEY_CHILD_CAT_AGE)).toInt()))
                } while (c.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (c != null) {
                    c.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return childageList
    }
    init {
        dbHelper = DbHelper.getInstance(context)
    }


    //insert data of TABLE_VARIANT
    @Synchronized
    fun insertVariantData(variants: Variants, productid: String) {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db!!.beginTransaction()
            val values = ContentValues()
            values.put(KEY_VARIANT_ID, variants.id)
            values.put(KEY_VARIANT_COLOR, variants.color)
            values.put(KEY_VARIANT_SIZE, variants.size)
            values.put(KEY_VARIANT_PRICE, variants.price)
            values.put(KEY_PRODUCT_ID, productid)
            // Inserting Row
            val insert = db.insert(TABLE_VARIANT, null, values)
            Log.v(TAG, "TABLE_VARIANT insert=$insert")
            db.setTransactionSuccessful()
            db.endTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //insert data of TABLE_CATEGORY
    @Synchronized
    fun insertCategory(itemCategory: ItemCategory) {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db!!.beginTransaction()
            val values = ContentValues()
            values.put(KEY_CATEGORY_ID, itemCategory.id)
            values.put(KEY_CATEGORY_NAME, itemCategory.name)
            // Inserting Row
            val insert = db.insert(TABLE_CATEGORY, null, values)
            Log.v(TAG, "TABLE_CATEGORY insert=$insert")
            db.setTransactionSuccessful()
            db.endTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //insert data of TABLE_PRODUCT
    @Synchronized
    fun insertPRODUCTS(catid: String, product: Product) {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db!!.beginTransaction()
            val values = ContentValues()
            values.put(KEY_CATEGORY_ID, catid)
            values.put(KEY_PRODUCT_NAME, product.name)
            values.put(KEY_PRODUCT_DATE, product.date_added)
            values.put(KEY_PRODUCT_ID, product.id)
            // Inserting Row
            val insert = db.insert(TABLE_PRODUCT, null, values)
            Log.v(TAG, "TABLE_PRODUCT insert=$insert")
            db.setTransactionSuccessful()
            db.endTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //insert data of TABLE_TAX
    @Synchronized
    fun insertTAX(productid: String, tax: Tax) {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db!!.beginTransaction()
            val values = ContentValues()
            values.put(KEY_TAX_VALUE, tax.value)
            values.put(KEY_TAX_NAME, tax.name)
            values.put(KEY_PRODUCT_ID, productid)
            // Inserting Row
            val insert = db.insert(TABLE_TAX, null, values)
            Log.v(TAG, "TABLE_TAX insert=$insert")
            db.setTransactionSuccessful()
            db.endTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //insert data of TABLE_CHILD_CATEGORIES
    @Synchronized
    fun insertChildCategories(catid: String, age: String) {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db!!.beginTransaction()
            val values = ContentValues()
            values.put(KEY_CHILD_CAT_AGE, age)
            values.put(KEY_CATEGORY_ID, catid)
            // Inserting Row
            val insert = db.insert(TABLE_CHILD_CATEGORIES, null, values)
            Log.v(TAG, "TABLE_CHILD_CATEGORIES insert=$insert")
            db.setTransactionSuccessful()
            db.endTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //insert data of TABLE_RANKING
    @Synchronized
    fun insertRanking(rankingtype: String, rankingProduct: RankingProduct) {
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            db!!.beginTransaction()
            val values = ContentValues()
            values.put(KEY_RANKING_TYPE, rankingtype)
            Log.v("DbTableinsertRanking","type: $rankingtype")
            Log.v("DbTableinsertRanking","count: ${rankingProduct.view_count}")
            values.put(KEY_RANKING_VIEW_COUNT, rankingProduct.view_count)
            values.put(KEY_PRODUCT_ID, rankingProduct.id)
            // Inserting Row
            val insert = db.insert(TABLE_RANKING, null, values)
            Log.v(TAG, "TABLE_RANKING insert=$insert")
            db.setTransactionSuccessful()
            db.endTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        private val TAG = DbTable::class.java.name
        // Tables for database
        val TABLE_VARIANT = "table_variant"
        val TABLE_TAX = "table_tax"
        val TABLE_PRODUCT = "table_product"
        val TABLE_CHILD_CATEGORIES = "table_child_categories"
        val TABLE_RANKING = "table_ranking"
        val TABLE_CATEGORY = "table_category"


        // All columns of TABLE_VARIANT
        val KEY_VARIANT_ID = "variant_id"
        val KEY_VARIANT_COLOR = "variant_color"
        val KEY_VARIANT_SIZE = "variant_size"
        val KEY_VARIANT_PRICE = "variant_price"

        // All columns of TABLE_TAX
        val KEY_TAX_NAME = "tax_name"
        val KEY_TAX_VALUE = "tax_value"

        //All columns of TABLE_PRODUCTS
        val KEY_PRODUCT_ID = "product_id"
        val KEY_PRODUCT_NAME = "product_name"
        val KEY_PRODUCT_DATE = "product_date"

        //All columns of TABLE_CHILD_CATEGORIES
        val KEY_CHILD_CAT_AGE = "child_cat_age"

        // All columns of TABLE_RANKING
        val KEY_RANKING_TYPE = "ranking_type"
        val KEY_RANKING_VIEW_COUNT = "ranking_view_count"

        // All columns of TABLE_CATEGORY
        val KEY_CATEGORY_ID = "category_id"
        val KEY_CATEGORY_NAME = "category_name"


        // query to create TABLE_VARIANT
        var CREATE_TABLE_VARIANT = ("CREATE TABLE IF NOT EXISTS " + TABLE_VARIANT + "("
                + KEY_VARIANT_ID + " TEXT,"
                + KEY_VARIANT_COLOR + " TEXT,"
                + KEY_VARIANT_SIZE + " TEXT,"
                + KEY_VARIANT_PRICE + " TEXT,"
                + KEY_PRODUCT_ID + " TEXT" + ")")

        // query to create TABLE_TAX
        var CREATE_TABLE_TAX = ("CREATE TABLE IF NOT EXISTS " + TABLE_TAX + "("
                + KEY_TAX_NAME + " TEXT,"
                + KEY_TAX_VALUE + " TEXT,"
                + KEY_PRODUCT_ID + " TEXT" + ")")

        // query to create TABLE_PRODUCT
        var CREATE_TABLE_PRODUCT = ("CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT + "("
                + KEY_PRODUCT_NAME + " TEXT,"
                + KEY_PRODUCT_DATE + " TEXT,"
                + KEY_CATEGORY_ID + " TEXT,"
                + KEY_PRODUCT_ID + " TEXT" + ")")

        // query to create TABLE_CHILD_CATEGORIES
        var CREATE_TABLE_CHILD_CATEGORIES = ("CREATE TABLE IF NOT EXISTS " + TABLE_CHILD_CATEGORIES + "("
                + KEY_CHILD_CAT_AGE + " TEXT,"
                + KEY_CATEGORY_ID + " TEXT" + ")")

        // query to create TABLE_RANKING
        var CREATE_TABLE_RANKING = ("CREATE TABLE IF NOT EXISTS " + TABLE_RANKING + "("
                + KEY_RANKING_TYPE + " TEXT,"
                + KEY_RANKING_VIEW_COUNT + " TEXT,"
                + KEY_PRODUCT_ID + " TEXT" + ")")


        // query to create TABLE_CATEGORY
        var CREATE_TABLE_CATEGORY = ("CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY + "("
                + KEY_CATEGORY_ID + " TEXT,"
                + KEY_CATEGORY_NAME + " TEXT" + ")")

        fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_TABLE_VARIANT)
            db.execSQL(CREATE_TABLE_TAX)
            db.execSQL(CREATE_TABLE_PRODUCT)
            db.execSQL(CREATE_TABLE_CHILD_CATEGORIES)
            db.execSQL(CREATE_TABLE_RANKING)
            db.execSQL(CREATE_TABLE_CATEGORY)
        }

        fun onUpgrade(db: SQLiteDatabase, Old_Ver: Int, New_Ver: Int) {
            onCreate(db)
        }
    }
}
