package com.adityarawat.kitlabstask.service.Response

import java.io.Serializable

data class UserData(
    val id: Int,
    val active_status: Int,
    val first_name: String,
    val last_name: String,
    val register_type: String,
    val name: String,
    val email: String,
    val email_verified_at: String?,
    val phone: String?,
    val location: String?,
    val fcm_token: String?,
    val socialLogID: String?,
    val image_path: String?,
    val registration_number: String?,
    val device_type: String,
    val about_user: String?,
    val last_updated_by: Int,
    val created_at: String,
    val updated_at: String,
    val user_role: Int,
    val token: String
):Serializable