package kr.hs.emirim.sookhee.redonorpets.ui.shelter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.sookhee.redonorpets.common.setImageWithUrl
import kr.hs.emirim.sookhee.redonorpets.databinding.ItemShelterGridBinding
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Shelter

/**
 *  ShelterAdapter.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class ShelterAdapter :
    RecyclerView.Adapter<ShelterAdapter.ViewHolder>() {

    private var items: List<Shelter> = emptyList()
    var onItemClick: ((selectedItem: Shelter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemShelterGridBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<Shelter>) {
        items = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemShelterGridBinding,
        onItemClick: ((selectedItem: Shelter) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        @SuppressLint("SetTextI18n")
        fun bind(
            item: Shelter,
        ) {
            binding.item = item

            binding.tvShelterName.text = item.name
            binding.tvStoryCount.text = "${item.storyCount}개의 스토리"
            binding.ivShelterProfile.setImageWithUrl(item.profileImage)
        }
    }
}
