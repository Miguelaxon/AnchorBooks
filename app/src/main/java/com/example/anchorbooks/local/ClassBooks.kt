package com.example.anchorbooks.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_books")
data class ClassBooks (@PrimaryKey val id: Int,
                       val author: String,
                       val country: String,
                       val imageLink: String,
                       val language: String,
                       val title: String)