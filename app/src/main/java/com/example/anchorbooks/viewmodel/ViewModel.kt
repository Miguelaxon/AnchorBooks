package com.example.anchorbooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.anchorbooks.local.BaseDatos
import com.example.anchorbooks.local.ClassBooks
import com.example.anchorbooks.local.ClassDetail
import com.example.anchorbooks.model.Repository
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {
    private val repository: Repository
    val listAllBooks: LiveData<List<ClassBooks>>
    private var idM: Int = 0

    init {
        val baseDatos = BaseDatos.getDataBase(application).getBooksDAO()
        repository = Repository(baseDatos)
        viewModelScope.launch {
            repository.getFetchBooksCoroutines()
        }
        listAllBooks = repository.listAllBooks
    }

    fun selectedBookDetail(id: Int) = viewModelScope.launch {
        idM = id
        repository.getFetchDetailCotoutines(id)
    }

    fun returnBookDetail(): LiveData<List<ClassDetail>> = repository.getBookDetail(idM)
}