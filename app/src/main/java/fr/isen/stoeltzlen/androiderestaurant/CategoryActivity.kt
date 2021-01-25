package fr.isen.stoeltzlen.androiderestaurant
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
enum class ItemType{
    STARTER,
    MAIN,
    DESSERT
}
class CategoryActivity : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        Log.d("lifecycle","onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle","onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle","onRestart")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle","onDestroy")
    }
}