package fr.isen.stoeltzlen.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.homeEntree.setOnClickListener{
            val intent = Intent ( packageContext: this, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, ItemType.HOMEENTREE)
            startActivity(intent)
        }

        binding.homePlat.setOnClickListener {
            val intent = Intent ( packageContext: this, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, ItemType.HOMEPLAT)
            startActivity(intent)

        }

        binding.homeDessert.setOnClickListener{
            val intent = Intent ( packageContext: this, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, ItemType.HOMEDESSERT)
            startActivity(intent)
        }



    }




}