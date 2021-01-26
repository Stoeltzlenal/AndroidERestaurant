package fr.isen.stoeltzlen.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log

import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder

import fr.isen.stoeltzlen.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.stoeltzlen.androiderestaurant.models.MenuResult
import org.json.JSONObject

enum class ItemType {
    STARTER, MAIN, DESSERT
}

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedItem = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType
        binding.categoryTitle.text = getCategoryTitle(selectedItem)

        if (binding.categoryTitle.text=="Entrées"){
            loadListStarter()

        }
        if (binding.categoryTitle.text=="Plats"){
            loadListMain()

        }
        if (binding.categoryTitle.text=="Dessert"){
            loadListDessert()

        }

        makeRequest()


        Log.d("lifecycle", "onCreate")
    }

    private fun loadListStarter() {
        var entries = listOf<String>("salade", "salade césar", "salade romaine")
        val adapter = CategoryAdapter(entries)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadListMain() {
        var entries = listOf<String>("boeuf", "jambon ", "carbonara")
        val adapter = CategoryAdapter(entries)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadListDessert() {
        var entries = listOf<String>("dame blanche", "vodka", "passion")
        val adapter = CategoryAdapter(entries)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getCategoryTitle(item: ItemType?): String {
        return when(item) {
            ItemType.STARTER -> getString(R.string.starter)
            ItemType.MAIN -> getString(R.string.main)
            ItemType.DESSERT -> getString(R.string.dessert)
            else -> ""
        }
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

    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val jsondata= JSONObject()
        jsondata.put("id_shop", 1)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val request = JsonObjectRequest(Request.Method.POST,
            url,
            jsondata,
            { response ->
                //success
                val menu = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
                menu.data.forEach{
                    Log.d("Request", it.name)
                }
            },
            { error ->
                Log.d("Request", error.localizedMessage)
            }
        )
        /*val request = StringRequest(Request.Method.GET,
            url,
            Response.Listener { response ->
                //success
                Log.d("Request", response)
            },
            Response.ErrorListener { error ->
                //error
                Log.d("Request", error.localizedMessage )
            }
        )*/
        queue.add(request)
    }
}