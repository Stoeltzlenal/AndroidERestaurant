package fr.isen.stoeltzlen.androiderestaurant

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.stoeltzlen.androiderestaurant.DetailActivity.Companion.USER_PREFERENCES_NAME

import fr.isen.stoeltzlen.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.stoeltzlen.androiderestaurant.models.NetworkConstant
import fr.isen.stoeltzlen.androiderestaurant.models.RegisterResult
import fr.isen.stoeltzlen.androiderestaurant.models.User
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            if(verifyInformations()) {
                launchRequest()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
            }
        }

        binding.registeLoginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    fun launchRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_REGISTER

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP, "1")
        jsonData.put(NetworkConstant.EMAIL, binding.registerEmail.text)
        jsonData.put(NetworkConstant.PASSWORD, binding.registerPassword.text)
        jsonData.put(NetworkConstant.FIRSTNAME, binding.registerFirstname)
        jsonData.put(NetworkConstant.LASTNAME, binding.registerLastname)

        var request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonData,
                { response ->
                    val userResult = GsonBuilder().create().fromJson(response.toString(), RegisterResult::class.java)
                    saveUser(userResult.data)
                },
                { error ->
                    error.message?.let {
                        Log.d("request", it)
                    } ?: run {
                        Log.d("request", error.toString())
                        Log.d("request", String(error.networkResponse.data))
                    }
                }
        )
        queue.add(request)
    }

    fun verifyInformations(): Boolean {
        return (binding.registerEmail.text?.isNotEmpty() == true &&
                binding.registerFirstname.text?.isNotEmpty() == true &&
                binding.registerLastname.text?.isNotEmpty() == true &&
                binding.registerPassword.text?.count() ?: 0 >= 6)
    }

    fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_FIRST_USER)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            setResult(Activity.RESULT_FIRST_USER)
            finish()
        }
    }

    companion object {
        const val REQUEST_CODE = 111
        const val ID_USER = "ID_USER"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}