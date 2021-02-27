package com.example.anchorbooks.remote

import com.example.anchorbooks.local.Books
import com.example.anchorbooks.local.BooksDetail
import com.google.gson.annotations.SerializedName

data class AnchorBooksList(val list: List<Books>)

data class AnchorBooksDetail(val list: List<BooksDetail>)