package com.adityarawat.kitlabstask.service.Response

data class registerUserData (
    val token: String,
    val first_name: String,
    val last_name: String,
    val name: String,
    val id: Int,
    val email: String,
    val active_status: Int,
    val register_type: String,
    val device_type: String,
    val password: String,
    val image_path: String?,
    val created_at: String,
    val updated_at: String,
    val socialLogID: String?,
    val user_role: Int
)