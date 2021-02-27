package com.example.anchorbooks.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.anchorbooks.local.Books
import com.example.anchorbooks.local.BooksDao
import com.example.anchorbooks.local.ClassBooks
import com.example.anchorbooks.local.ClassDetail
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

    fun converterDetail(list: List<String>, id: Int): List<ClassDetail>{
        val listDetail: MutableList<ClassDetail> = mutableListOf()
        list.map {
            //listDetail.add(ClassDetail(id = id, ))
        }
        return listDetail
    }
}