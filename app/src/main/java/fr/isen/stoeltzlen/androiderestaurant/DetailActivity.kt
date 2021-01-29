package fr.isen.stoeltzlen.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.stoeltzlen.androiderestaurant.R
import fr.isen.stoeltzlen.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.stoeltzlen.androiderestaurant.models.Item


private lateinit var binding: ActivityDetailBinding
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getSerializableExtra("dish") as Item
        binding.ingredients1.text = dish.ingredients.map { it.name }.joinToString (" ,")

        dish.getAllPictures()?.let {
            binding.detailPager.adapter = DetailViewAdapter(this, it)
        }
    }
}