package com.example.anchorbooks.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.example.anchorbooks.R
import com.example.anchorbooks.databinding.ItemBooksBinding
import com.example.anchorbooks.local.ClassBooks

class AdapterBooks: RecyclerView.Adapter<AdapterBooks.BooksViewHolder>() {
    private var listBooks = listOf<ClassBooks>()
    private val selectedBooks = MutableLiveData<ClassBooks>()

    fun selectedBooks(): LiveData<ClassBooks> = selectedBooks

    fun update(list: List<ClassBooks>){
        listBooks = list
        notifyDataSetChanged()
    }

    inner class BooksViewHolder(private val binding: ItemBooksBinding):
            RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        fun bind(classBooks: ClassBooks){
            binding.tvTitle.text = classBooks.title.toUpperCase()
            binding.tvAuthor.text = classBooks.author
            binding.tvCountry.text = classBooks.country
            binding.tvLanguage.text = classBooks.language
            Glide.with(binding.ivBooks).load(classBooks.imageLink).into(binding.ivBooks)
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            selectedBooks.value =listBooks[adapterPosition]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        return BooksViewHolder(ItemBooksBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val list = listBooks[position]
        holder.bind(list)
    }

    override fun getItemCount(): Int = listBooks.size
}