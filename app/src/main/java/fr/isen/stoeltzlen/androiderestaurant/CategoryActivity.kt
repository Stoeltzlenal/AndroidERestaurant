package fr.isen.stoeltzlen.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.util.JsonReader
import android.util.Log

import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request

import com.android.volley.toolbox.JsonObjectRequest

import com.android.volley.toolbox.Volley
import com.example.isen_2021.category.CategoryAdapter
import com.google.gson.GsonBuilder

import fr.isen.stoeltzlen.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.stoeltzlen.androiderestaurant.models.Item
import fr.isen.stoeltzlen.androiderestaurant.models.MenuResult
import fr.isen.stoeltzlen.androiderestaurant.models.NetworkConstant
import org.json.JSONObject

enum class ItemType {
    STARTER, MAIN, DESSERTS;

    companion object {
        fun categoryTitle(item: ItemType?) : String {
            return when(item) {
                STARTER -> "Entrées"
                MAIN -> "Plats"
                DESSERTS -> "Desserts"
                else -> ""
            }
        }
    }
}

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedItem = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType
        binding.categoryTitle.text = getCategoryTitle(selectedItem)

        loadList(listOf<Item>())

        makeRequest(selectedItem)
        Log.d("lifecycle", "onCreate")
    }

    private fun loadList(dishes: List<Item>?) {
        dishes?.let {
            val adapter = CategoryAdapter(it) { dish ->
                //TODO afficher activité detail
                Log.d("dish", "selected dish ${dish.name}")
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun getCategoryTitle(item: ItemType?): String {
        return when(item) {
            ItemType.STARTER -> getString(R.string.starter)
            ItemType.MAIN -> getString(R.string.main)
            ItemType.DESSERTS -> getString(R.string.desserts)
            else -> ""
        }
    }

    private fun makeRequest(selectedItem: ItemType?) {
        resultFromCache()?.let {
            // La requete est en cache
            parseResult(it, selectedItem)
        } ?: run {
            // La requete n'est pas en cache
            val queue = Volley.newRequestQueue(this)
            val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_MENU

            val jsonData = JSONObject()
            jsonData.put(NetworkConstant.ID_SHOP, "1")

            var request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonData,
                { response ->
                    cacheResult(response.toString())
                    parseResult(response.toString(), selectedItem)
                },
                { error ->
                    error.message?.let {
                        Log.d("request", it)
                    } ?: run {
                        Log.d("request", error.toString())
                    }
                }
            )
            queue.add(request)
        }
    }

    private fun cacheResult(response: String) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(REQUEST_CACHE, response)
        editor.apply()
    }

    private fun resultFromCache(): String? {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(REQUEST_CACHE, null)
    }

    private fun parseResult(response: String, selectedItem: ItemType?) {
        val menuResult = GsonBuilder().create().fromJson(response, MenuResult::class.java)
        val items = menuResult.data.firstOrNull { it.name == ItemType.categoryTitle(selectedItem) }
        loadList(items?.items)
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "onRestart")
    }

    override fun onDestroy() {
        Log.d("lifecycle", "onDestroy")
        super.onDestroy()
    }

    companion object {
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
        const val REQUEST_CACHE = "REQUEST_CACHE"
    }


}