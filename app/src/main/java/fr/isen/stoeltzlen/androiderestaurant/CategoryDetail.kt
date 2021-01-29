package fr.isen.stoeltzlen.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import fr.isen.stoeltzlen.androiderestaurant.models.Item

class CategoryDetail : AppCompatActivity() {
   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)

        binding = CategoryDetail.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getSerializableExtra("Category") as Item
        binding.dishTitle.text = dish.name

        binding.dishIngredients.text = dish.getIngredient();
        Picasso.get()
            .load(dish.getFirstPicture())
            .resize(400,200)
            .into(imageView)

    }*/
}