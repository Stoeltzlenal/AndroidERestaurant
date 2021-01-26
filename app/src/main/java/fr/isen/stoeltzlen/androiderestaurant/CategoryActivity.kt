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
import fr.isen.stoeltzlen.androiderestaurant.models.Item
import fr.isen.stoeltzlen.androiderestaurant.models.MenuResult
import org.json.JSONObject

enum class ItemType {
    STARTER, MAIN, DESSERTS
}

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedItem = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType
        val categoryTitle = getCategoryTitle(selectedItem)

        makeRequest(categoryTitle)
        binding.categoryTitle.text = categoryTitle

        //loadList()

        Log.d("lifecycle", "onCreate")
    }

    private fun loadList(dishes: List<Item>?) {
        val entries = dishes?.map { it.name }
        entries?.let {
            val adapter = CategoryAdapter(entries)
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

    private fun makeRequest(title:String) {
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
                val dishes = menu.data.firstOrNull{
                    it.name == title
                }?.items
                if (dishes != null) {
                    loadList(dishes)
                } else{
                    Log.e("CategoryActivity", "no category")
                }
            },
            { error ->
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                }
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