package kr.hs.emirim.sookhee.redonorpets.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.sookhee.redonorpets.common.setImageWithUrl
import kr.hs.emirim.sookhee.redonorpets.databinding.ItemStoryBinding
import kr.hs.emirim.sookhee.redonorpets.domain.entity.StoryFeed

/**
 *  StoryFeedAdapter.kt
 *
 *  Created by Minji Jeong on 2022/01/07
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class StoryFeedAdapter :
    RecyclerView.Adapter<StoryFeedAdapter.ViewHolder>() {

    private var items: List<StoryFeed> = emptyList()
    var onItemClick: ((selectedItem: StoryFeed) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemStoryBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<StoryFeed>) {
        items = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemStoryBinding,
        onItemClick: ((selectedItem: StoryFeed) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        fun bind(
            item: StoryFeed,
        ) {
            binding.item = item
            binding.storyThumbnail.setImageWithUrl(item.thumbnail)
        }
    }
}
