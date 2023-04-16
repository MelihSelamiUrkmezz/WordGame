package com.example.wordgame

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {
    @Headers("Content-Type: application/json", "Access-Control-Allow-Origin: http://34.133.240.128:8000")
    @POST("/word_check")
    fun check_word(@Body request: Request) :Call<Response>


}