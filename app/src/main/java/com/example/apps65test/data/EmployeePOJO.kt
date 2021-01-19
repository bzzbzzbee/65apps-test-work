package com.example.apps65test.data

data class Specialty (
    val specialty_id: Int,
    val name: String?
)

data class Response(
    val f_name: String?,
    val l_name: String?,
    val birthday: String?,
    val avatr_url: String?,
    val specialty: List<Specialty>?
)

data class Root (
    val response: List<Response>
)