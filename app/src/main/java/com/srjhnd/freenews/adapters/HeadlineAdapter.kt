package com.srjhnd.freenews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.srjhnd.freenews.FeedFragmentDirections
import com.srjhnd.freenews.R
import com.srjhnd.freenews.data.Headline
import com.srjhnd.freenews.databinding.HeadlineItemBinding

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
            binding.headline = headline
            binding.setClickListener { view ->
                binding.headline?.title?.let {
                    navigateHeadlineView(it, view)
                }
            }
            binding.executePendingBindings()
        }

        private fun navigateHeadlineView(title: String, view: View) {
            val direction = FeedFragmentDirections.actionFeedFragmentToHeadlineFragment(title)
            view.findNavController().navigate(direction)
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
