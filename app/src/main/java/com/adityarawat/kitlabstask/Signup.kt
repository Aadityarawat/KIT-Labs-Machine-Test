package com.adityarawat.kitlabstask

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.adityarawat.kitlabstask.model.RegisterData
import com.adityarawat.kitlabstask.service.Response.LoginResponse
import com.adityarawat.kitlabstask.service.Response.RegisterResponse
import com.adityarawat.kitlabstask.service.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Signup : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private var mProgressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val logintv = findViewById<TextView>(R.id.logintv)

        logintv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val backiv = findViewById<ImageView>(R.id.backiv)

        backiv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val signinBtn = findViewById<Button>(R.id.signupbtn)

        signinBtn.setOnClickListener {
            signinRequest()
        }
    }

    private fun signinRequest() {

        val email = findViewById<EditText>(R.id.mailet)
        val password = findViewById<EditText>(R.id.passet)
        val firstName = findViewById<EditText>(R.id.firstnameet)
        val lastName = findViewById<EditText>(R.id.lastnameet)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://demo.kitlabs.us/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Service::class.java)

        val sendData = RegisterData(
            email = email.text.toString(),
            password = password.text.toString(),
            first_name = firstName.text.toString(),
            last_name = lastName.text.toString(),
            register_type = "Native",
            device_type = "android",
            role_type = "family"
        )
        showCustomProgressDialog()
        service.register(sendData).enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    hideProgressDialog()
                    val registerResponse = response.body()
                    Toast.makeText(this@Signup,"Message: ${registerResponse?.status}",
                        Toast.LENGTH_LONG).show()

                    val intent = Intent(this@Signup,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Signup,"Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_LONG).show()
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(
                    this@Signup, "Error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun showCustomProgressDialog(){
        mProgressDialog = Dialog(this)
        mProgressDialog!!.setContentView(R.layout.custom_progress_dialog)
        mProgressDialog!!.show()
    }

    private fun hideProgressDialog(){
        if(mProgressDialog!=null){
            mProgressDialog!!.dismiss()
        }
    }
}