package com.adityarawat.kitlabstask

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityarawat.kitlabstask.adapter.MyAdapter
import com.adityarawat.kitlabstask.model.Constant
import com.adityarawat.kitlabstask.model.FamilyItem
import com.adityarawat.kitlabstask.model.LoginData
import com.adityarawat.kitlabstask.service.Response.UserData
import com.adityarawat.kitlabstask.service.Response.recentRequestResponse
import com.adityarawat.kitlabstask.service.Service
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecentRequest : AppCompatActivity() {
    lateinit var token : String
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList : ArrayList<FamilyItem>
    lateinit var image : Array<Int>
    lateinit var position : Array<String>
    lateinit var phone : Array<String>
    lateinit var status : Array<String>
    lateinit var gender : Array<String>

    private var mProgressDialog: Dialog? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_request)

        val searchView = findViewById<SearchView>(R.id.search)
        val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(ContextCompat.getColor(this, R.color.black))
        editText.setHintTextColor(ContextCompat.getColor(this, R.color.grey))

        // Store the obtained token
        token = Constant.token

        // Make the GET request
        image = arrayOf(
            R.drawable.bg1,
            R.drawable.bg2,
            R.drawable.bg3,
            R.drawable.bg4,
            R.drawable.bg5
        )

        position = arrayOf(
            "Medical Doctor",
            "Medical Nurse",
            "Medical Doctor",
            "Assistant",
            "Medical Doctor"
        )

        phone = arrayOf(
            "8795888888",
            "9988888123",
            "8768884444",
            "7894888888",
            "9756888888"
        )
        status = arrayOf(
            "Accepted",
            "InProgress",
            "Accepted",
            "Closed",
            "Accepted"
        )

        gender = arrayOf(
            "male",
            "Female",
            "male",
            "Female",
            "male"
        )

        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<FamilyItem>()
        getUserData()

        makeGetRequest()
    }

    private fun getUserData() {
        for (i in image.indices){
            val Familydata = FamilyItem(image[i],position[i],phone[i],status[i],gender[i])
            newArrayList.add(Familydata)
        }

        newRecyclerView.adapter = MyAdapter(newArrayList)
    }

    private fun makeGetRequest() {
        val client = OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            })
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://demo.kitlabs.us/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service interface
        val service = retrofit.create(Service::class.java)
        showCustomProgressDialog()
        service.recentRequest().enqueue(object : Callback<recentRequestResponse>{
            override fun onResponse(
                call: Call<recentRequestResponse>,
                response: Response<recentRequestResponse>
            ) {
                if (response.isSuccessful) {
                    hideProgressDialog()
                    val responseData = response.body()
                    Log.i("responsedata : ","$responseData")
                    Toast.makeText(
                        this@RecentRequest, "@Get Data : ${responseData}",
                        Toast.LENGTH_LONG
                    ).show()

                }  else {
                    Toast.makeText(
                        this@RecentRequest, "Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<recentRequestResponse>, t: Throwable) {

                Toast.makeText(
                    this@RecentRequest, "Error: ${t.message}",
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