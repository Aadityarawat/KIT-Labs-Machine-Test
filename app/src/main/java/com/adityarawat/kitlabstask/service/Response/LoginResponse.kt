package com.adityarawat.kitlabstask.service.Response

data class LoginResponse(
    val status: String,
    val message: Message,
    val data: UserData
)