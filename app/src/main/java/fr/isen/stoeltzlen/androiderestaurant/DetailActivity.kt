package fr.isen.stoeltzlen.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.stoeltzlen.androiderestaurant.R
import fr.isen.stoeltzlen.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.stoeltzlen.androiderestaurant.models.Basket
import fr.isen.stoeltzlen.androiderestaurant.models.BasketItem
import fr.isen.stoeltzlen.androiderestaurant.models.Item

import org.json.JSONObject
import java.io.File
import kotlin.math.max


private lateinit var binding: ActivityDetailBinding
class DetailActivity : AppCompatActivity() {
    private var itemCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getSerializableExtra("dish") as Item
        binding.ingredients1.text = dish.ingredients.map { it.name }.joinToString (" ,")

        dish.getAllPictures()?.let {
            binding.detailPager.adapter = DetailViewAdapter(this, it)
        }

        binding.dishTitleTextView.text = dish.name

        binding.less.setOnClickListener {
            itemCount = max(1, itemCount - 1)
            refreshShop(dish)
        }

        binding.more.setOnClickListener {
            itemCount += 1
            refreshShop(dish)
        }

        binding.shopButton.setOnClickListener {
            addToBasket(dish, itemCount)
        }
    }

    private fun refreshShop(dish: Item) {
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.itemCount.text = itemCount.toString()
        binding.shopButton.text = "${getString(R.string.total)} $price€"
    }

    private fun addToBasket(dish: Item, count: Int) {
        val basket = Basket.getBasket(this)
        basket.addItem(BasketItem(dish, count))
        basket.save(this)
        val json = GsonBuilder().setPrettyPrinting().create().toJson(basket)
        Log.d("basket", json)
    }


}





