package com.example.anchorbooks.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anchorbooks.R
import com.example.anchorbooks.databinding.ItemBookDetailBinding
import com.example.anchorbooks.local.ClassBooks
import com.example.anchorbooks.local.ClassDetail

class AdapterDetail: RecyclerView.Adapter<AdapterDetail.DetailBookViewHolder>() {
    private var listDetail = listOf<ClassDetail>()
    private val selectedDetail = MutableLiveData<ClassDetail>()

    fun selectedBookDetail(): LiveData<ClassDetail> = selectedDetail

    fun update(list: List<ClassDetail>){
        listDetail = list
        notifyDataSetChanged()
    }

    inner class DetailBookViewHolder(private val binding: ItemBookDetailBinding):
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener{
        fun bind(classDetail: ClassDetail){
            binding.tvTitleD.text = classDetail.title.toUpperCase()
            binding.tvAuthorD.text = classDetail.author
            binding.tvPriceD.text = classDetail.price.toString()
            binding.tvPages.text = classDetail.pages.toString()
            binding.wvDescription.loadUrl(classDetail.link)
            Glide.with(binding.ivBookDetail).load(classDetail.imageLink).into(binding.ivBookDetail)
            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            selectedDetail.value = listDetail[adapterPosition]
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailBookViewHolder {
        return DetailBookViewHolder(ItemBookDetailBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DetailBookViewHolder, position: Int) {
        val list = listDetail[position]
        holder.bind(list)
    }

    override fun getItemCount(): Int = listDetail.size
}