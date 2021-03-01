package com.example.anchorbooks.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.anchorbooks.local.*
import com.example.anchorbooks.remote.ApiClient

class Repository (private val booksDao: BooksDao) {
    val listAllBooks: LiveData<List<ClassBooks>> = booksDao.getAllBooks()

    fun converterBooks(list: List<Books>): List<ClassBooks>{
        val listBooks: MutableList<ClassBooks> = mutableListOf()
        list.map {
            listBooks.add(ClassBooks(id = it.id, author = it.author, country = it.country,
            imageLink = it.imageLink, language = it.language, title = it.title))
        }
        return listBooks
    }

    suspend fun getFetchBooksCoroutines(){
        try {
            val response = ApiClient.getApiClient().getFetchBooks()
            when (response.isSuccessful){
                true -> response.body()?.let {
                    booksDao.insertAllBooks(converterBooks(it.list))
                }
                false -> Log.d("ERROR", "${response.code()}: ${response.errorBody()}")
            }
        } catch (t: Throwable){
            Log.d("Error Books Coroutine", t.message.toString())
        }
    }

    fun converterDetail(id: Int, author: String, country: String, imageLink: String,
                        language: String, link: String, pages: Int, title: String, year: Int,
                        price: Int, lastPrice: Int, delivery: Boolean): List<ClassDetail>{
        val listDetail: MutableList<ClassDetail> = mutableListOf()
            listDetail.add(ClassDetail(id = id, author = author, country = country,
                    imageLink = imageLink, language = language, link = link, pages = pages,
                    title = title, year = year, price = price, lastPrice = lastPrice,
                    delivery = delivery))
        return listDetail
    }

    suspend fun getFetchDetailCotoutines(id: Int){
        try {
            val response = ApiClient.getApiClient().getFetchBooksDetail(id)
            when (response.isSuccessful){
                true -> response.body()?.let {
                    booksDao.insertAllBooksDetail(converterDetail(id, it.author, it.country,
                            it.imageLink, it.language, it.link, it.pages, it.title, it.year,
                            it.price, it.lastPrice, it.delivery))
                }
                false -> Log.d("ERROR", "${response.code()}: ${response.errorBody()}")
            }
        } catch (t: Throwable){
            Log.d("Error Detail Coroutine", t.message.toString())
        }
    }

    fun getBookDetail(id: Int): LiveData<List<ClassDetail>> = booksDao.getBooksDetail(id)
}