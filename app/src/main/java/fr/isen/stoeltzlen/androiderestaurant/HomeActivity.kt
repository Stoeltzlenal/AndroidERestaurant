package fr.isen.stoeltzlen.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import fr.isen.stoeltzlen.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.starter.setOnClickListener{
            val intent = Intent ( this, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, ItemType.STARTER)
            startActivity(intent)
        }

        binding.main.setOnClickListener {
            val intent = Intent (this, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, ItemType.MAIN)
            startActivity(intent)
        }

        binding.desserts.setOnClickListener{
            val intent = Intent ( this, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, ItemType.DESSERTS)
            startActivity(intent)
        }
    }
    private fun statCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }

    companion object {
        const val CATEGORY_NAME = "categoryname"
    }
}