package com.adityarawat.kitlabstask.model

data class RegisterData (
    val first_name : String,
    val last_name : String,
    val email : String,
    val password : String,
    val register_type :String,
    val device_type :String,
    val role_type :String
)