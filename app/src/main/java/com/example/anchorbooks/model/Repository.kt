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
            Log.d("Error Coroutine", t.message.toString())
        }
    }

    fun converterDetail(list: List<BooksDetail>, id: Int): List<ClassDetail>{
        val listDetail: MutableList<ClassDetail> = mutableListOf()
        list.map {
            listDetail.add(ClassDetail(id = id, author = it.author, country = it.country,
            imageLink = it.imageLink, language = it.language, link = it.link, pages = it.pages,
            title = it.title, year = it.year, price = it.price, lastPrice = it.lastPrice,
                delivery = it.delivery))
        }
        return listDetail
    }

    suspend fun getFetchDetailCotoutines(id: Int){
        try {
            val response = ApiClient.getApiClient().getFetchBooksDetail(id)
            when (response.isSuccessful){
                true -> response.body()?.let {
                    booksDao.insertAllBooksDetail(converterDetail(it.list, id))
                }
                false -> Log.d("ERROR", "${response.code()}: ${response.errorBody()}")
            }
        } catch (t: Throwable){
            Log.d("Error Coroutine", t.message.toString())
        }
    }

    fun getBookDetail(id: Int): LiveData<List<ClassDetail>> = booksDao.getBooksDetail(id)
}