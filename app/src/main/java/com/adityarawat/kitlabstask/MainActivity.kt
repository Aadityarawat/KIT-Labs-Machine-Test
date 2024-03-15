package com.adityarawat.kitlabstask

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.adityarawat.kitlabstask.model.Constant
import com.adityarawat.kitlabstask.model.LoginData
import com.adityarawat.kitlabstask.service.Response.LoginResponse
import com.adityarawat.kitlabstask.service.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var mProgressDialog: Dialog? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signbtn = findViewById<TextView>(R.id.signuptv)

        signbtn.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        val loginbtn = findViewById<Button>(R.id.loginbtn)

        loginbtn.setOnClickListener {
            recentRequestScreen()
        }

    }

    private fun recentRequestScreen() {
        val emaill = findViewById<EditText>(R.id.emailet)
        val passwordd = findViewById<EditText>(R.id.passwordet)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://demo.kitlabs.us/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Service::class.java)

        val sendData = LoginData(
            email = emaill.text.toString(),
            password = passwordd.text.toString()
        )
        showCustomProgressDialog()
        service.login(sendData).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    hideProgressDialog()
                    val registerResponse = response.body()
                    Toast.makeText(this@MainActivity,"Message: ${registerResponse?.status}",
                        Toast.LENGTH_LONG).show()
                    Constant.token = registerResponse?.message?.token ?: ""
                    if (Constant.token != ""){
                        val intent = Intent(this@MainActivity,RecentRequest::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@MainActivity,"Message: Token is Empty",
                            Toast.LENGTH_LONG).show()
                    }


                } else {
                    Toast.makeText(this@MainActivity,"Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_LONG).show()
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity, "Error: ${t.message}",
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