package com.hylink.chexunjingwu.http.request

data class HomeLoginRequest(
    val username: String? = null,
    val password: String? = null,
    val sid: String = "ac2b965d-3928-4eda-a8af-a3a4a0f9f982",
    val idCard: String? = null
)