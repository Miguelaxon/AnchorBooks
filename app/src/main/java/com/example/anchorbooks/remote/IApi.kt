package com.example.anchorbooks.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IApi {
    @GET("db")
    suspend fun getFetchBooks(): Response<AnchorBooksList>

    @GET("bookDetail/{id}")
    suspend fun getFetchBooksDetail(@Path("id") id: Int): Response<AnchorBooksDetail>
}