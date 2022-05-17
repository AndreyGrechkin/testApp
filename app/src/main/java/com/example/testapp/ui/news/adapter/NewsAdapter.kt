package com.example.testapp.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.databinding.NewsItemBinding
import com.example.testapp.model.NewsList

class NewsAdapter : PagingDataAdapter<NewsList, NewsAdapter.Holder>(NewsDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val news = getItem(position) ?: return
        holder.bind(news)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    class Holder(
        private val binding: NewsItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(news: NewsList) {
            binding.titleNews.text = news.title

            Glide.with(itemView)
                .load(news.imageUrl)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(binding.newsImage)
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<NewsList>() {
        override fun areItemsTheSame(oldItem: NewsList, newItem: NewsList): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsList, newItem: NewsList): Boolean {
           return oldItem == newItem
        }

    }


}