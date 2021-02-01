package fr.isen.stoeltzlen.androiderestaurant

import android.content.Context
import android.os.Bundle
import com.example.isen_2021.BaseActivity
import fr.isen.stoeltzlen.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.stoeltzlen.androiderestaurant.models.Basket
import fr.isen.stoeltzlen.androiderestaurant.models.BasketItem
import fr.isen.stoeltzlen.androiderestaurant.models.Item
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.io.File
import kotlin.math.max


private lateinit var binding: ActivityDetailBinding
class DetailActivity : BaseActivity() {
    companion object {
        const val DISH_EXTRA = "DISH_EXTRA"
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }

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
        refreshMenu(basket)
        Snackbar.make(binding.root, getString(R.string.basket_validation), Snackbar.LENGTH_LONG).show()
    }

    private fun refreshMenu(basket: Basket) {
        val count = basket.itemsCount
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, count)
        editor.apply()
        invalidateOptionsMenu() // refresh l'affichage du menu
    }


}





