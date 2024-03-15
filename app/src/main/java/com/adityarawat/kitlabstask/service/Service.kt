package com.adityarawat.kitlabstask.service

import com.adityarawat.kitlabstask.model.LoginData
import com.adityarawat.kitlabstask.model.RegisterData
import com.adityarawat.kitlabstask.service.Response.LoginResponse
import com.adityarawat.kitlabstask.service.Response.RegisterResponse
import com.adityarawat.kitlabstask.service.Response.recentRequestResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @POST("api/login")
    fun login(@Body request: LoginData): Call<LoginResponse>

    @POST("api/register")
    fun register(@Body request: RegisterData): Call<RegisterResponse>

    @GET("api/get_family_request")
    fun recentRequest(): Call<recentRequestResponse>
}