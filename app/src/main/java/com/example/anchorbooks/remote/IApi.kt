package com.example.anchorbooks.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IApi {
    @GET("anchorBooks/books")
    suspend fun getFetchBooks(): Response<AnchorBooksList>

    @GET("anchorBooks/bookDetail/{id}")
    suspend fun getFetchBooksDetail(@Path("id") id: Int): Response<AnchorBooksDetail>
}