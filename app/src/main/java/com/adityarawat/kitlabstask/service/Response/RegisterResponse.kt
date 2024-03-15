package com.adityarawat.kitlabstask.service.Response

import java.io.Serializable

data class RegisterResponse (
    val status : String,
    val message: String,
    val data : registerUserData
): Serializable