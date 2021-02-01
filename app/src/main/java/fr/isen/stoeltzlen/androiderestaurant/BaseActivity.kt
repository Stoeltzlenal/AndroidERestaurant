package com.example.isen_2021

import android.content.Context
import android.util.Log
import android.content.Intent
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.isen.stoeltzlen.androiderestaurant.BasketActivity
import fr.isen.stoeltzlen.androiderestaurant.DetailActivity
import fr.isen.stoeltzlen.androiderestaurant.R


open class BaseActivity: AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu?.findItem(R.id.basket)?.actionView
        val countText = menuView?.findViewById(R.id.basketCount) as? TextView
        val count = getItemsCount()
        countText?.isVisible = count > 0

        countText?.text = count.toString()

        menuView?.setOnClickListener {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }

        return true
    }
    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    private fun getItemsCount(): Int {
        val sharedPreferences = getSharedPreferences(DetailActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(DetailActivity.ITEMS_COUNT, 0)
    }
}