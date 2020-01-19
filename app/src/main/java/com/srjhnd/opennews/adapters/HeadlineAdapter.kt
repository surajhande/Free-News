package com.srjhnd.opennews.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.srjhnd.opennews.R
import com.srjhnd.opennews.data.Headline
import com.srjhnd.opennews.databinding.HeadlineItemBinding

class HeadlineAdapter :
    ListAdapter<Headline, HeadlineAdapter.HeadlineViewHolder>(HeadlineDiffCallback()) {
    private lateinit var binding: HeadlineItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.headline_item,
            parent, false
        )
        return HeadlineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HeadlineViewHolder(val binding: HeadlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(headline: Headline) {
            binding.invalidateAll()
            binding.headline = headline
            binding.executePendingBindings()
        }
    }

    private class HeadlineDiffCallback : DiffUtil.ItemCallback<Headline>() {
        override fun areItemsTheSame(oldItem: Headline, newItem: Headline): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Headline, newItem: Headline): Boolean {
            return oldItem.title == newItem.title
        }

    }
}
