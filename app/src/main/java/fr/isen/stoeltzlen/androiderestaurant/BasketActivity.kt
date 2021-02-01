package fr.isen.stoeltzlen.androiderestaurant

import fr.isen.stoeltzlen.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.stoeltzlen.androiderestaurant.models.Basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager


class BasketActivity : AppCompatActivity()/*, BasketCellInterface */{
    lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reloadData(Basket.getBasket(this))
    }

    private fun reloadData(basket: Basket) {
        binding.basketRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.basketRecyclerView.adapter = BasketAdapter(basket, this) {
            val basket2 = Basket.getBasket(this)
            val itemToDelete = basket2.items.firstOrNull { it.dish.name == it.dish.name }
            basket2.items.remove(itemToDelete)
            basket2.save(this)
            reloadData(basket2)
        }
    }

}