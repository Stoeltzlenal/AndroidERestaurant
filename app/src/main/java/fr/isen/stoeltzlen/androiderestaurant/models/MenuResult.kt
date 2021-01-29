package fr.isen.stoeltzlen.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MenuResult (val data: List<Category>){}

//Affiche les categories
class Category (@SerializedName("name_fr") val name:String, val items:List<Item>){}


class Item(
    @SerializedName("name_fr") val name: String,
    val ingredients: List<Ingredient>,
    val images: List<String>,
    val prices: List<Price>
) {
    fun getFirstPicture() = if (images.isNotEmpty() && images[0].isNotEmpty()) {
        images[0]
    } else {
        null
    }
}

class Ingredient (@SerializedName("name_fr") val name:String){}

class Price (val price:String){}



