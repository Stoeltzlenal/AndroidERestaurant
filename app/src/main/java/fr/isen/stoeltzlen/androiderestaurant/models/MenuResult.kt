package fr.isen.stoeltzlen.androiderestaurant.models

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.Serializable

class MenuResult (val data: List<Category>){}

class Category (@SerializedName("name_fr") val name:String,
                @SerializedName("items")val items:List<Item>){}

class Item(
    @SerializedName("name_fr") val name: String,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("images") val images: List<String>,
    @SerializedName("prices") val prices: List<Price>
): Serializable {
    fun getFirstPicture() = if (images.isNotEmpty() && images[0].isNotEmpty()) {
        images[0]
    } else {
        null
    }

    fun getAllPictures() = if (images.isNotEmpty() && images.any { it.isNotEmpty() }) {
        images.filter { it.isNotEmpty() }
    } else {
        null
    }

    //fun getPrice()=prices[0].price.toDouble()
}

class Ingredient (@SerializedName("name_fr") val name:String):Serializable{}

class Price (val price:String):Serializable{}

class NetworkConstant {
    companion object {
        const val BASE_URL = "http://test.api.catering.bluecodegames.com/"
        const val PATH_MENU = "menu"
        const val ID_SHOP = "id_shop"
    }
}

class Basket (val items: MutableList<BasketItem>): Serializable {

    fun addItem(item: BasketItem) {
        val existingItem = items.firstOrNull {
            it.dish.name == item.dish.name
        }
        existingItem?.let {
            existingItem.count += item.count
        } ?: run {
            items.add(item)
        }
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        jsonFile.writeText(GsonBuilder().setPrettyPrinting().create().toJson(this))
    }

    companion object {
        fun getBasket(context: Context): Basket {
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
            return if(jsonFile.exists()) {
                val json = jsonFile.readText()
                GsonBuilder().setPrettyPrinting().create().fromJson(json, Basket::class.java)
            } else {
                Basket(mutableListOf())
            }
        }

        const val BASKET_FILE = "basket.json"
    }
}

class BasketItem(val dish: Item, var count: Int): Serializable {}