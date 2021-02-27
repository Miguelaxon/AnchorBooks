package com.example.anchorbooks.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BooksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooks(books: List<ClassBooks>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooksDetail(detail: List<ClassDetail>)

    @Query("SELECT * FROM table_books")
    fun getAllBooks(): LiveData<List<ClassBooks>>

    @Query("SELECT * FROM table_detail WHERE id = :idBooks")
    fun getBooksDetail(idBooks: Int): LiveData<List<ClassDetail>>
}