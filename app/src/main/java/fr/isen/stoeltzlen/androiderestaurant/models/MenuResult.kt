package fr.isen.stoeltzlen.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MenuResult (val data: List<Category>){}

//
class Category (@SerializedName("name_fr") val name:String,
                @SerializedName("items")val items:List<Item>){}


class Item(
    @SerializedName("name_fr") val name: String,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("images") val images: List<String>,
    @SerializedName("prices") val prices: List<Price>
): Serializable
{

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



