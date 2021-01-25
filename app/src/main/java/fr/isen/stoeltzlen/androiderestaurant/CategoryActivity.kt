package fr.isen.stoeltzlen.androiderestaurant

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        Log.d( tag: "lifecycle", msg: "onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.d( tag: "lifecycle", msg: "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d( tag: "lifecycle", msg: "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d( tag: "lifecycle", msg: "onDestroy")
    }
}