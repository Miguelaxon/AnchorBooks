package com.example.anchorbooks.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
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

    fun update(list: List<ClassDetail>){
        listDetail = list
        notifyDataSetChanged()
    }

    inner class DetailBookViewHolder(private val binding: ItemBookDetailBinding):
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        fun bind(classDetail: ClassDetail){
            binding.tvTitleD.text = classDetail.title.toUpperCase()
            binding.tvAuthorD.text = classDetail.author
            binding.tvLastPriceD.text = classDetail.lastPrice.toString()
            binding.tvPriceD.text = classDetail.price.toString()
            binding.tvPages.text = classDetail.pages.toString()
            Glide.with(binding.ivBookDetail).load(classDetail.imageLink).into(binding.ivBookDetail)
            binding.ivDelivery.isVisible = classDetail.delivery
            binding.wvWiki.loadUrl(classDetail.link)
            binding.wvWiki.webViewClient = object : WebViewClient(){}
            val setting: WebSettings = binding.wvWiki.settings
            setting.javaScriptEnabled = true
            binding.wvWiki.loadUrl(classDetail.link)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            selectedDetail.value = listDetail[adapterPosition]
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