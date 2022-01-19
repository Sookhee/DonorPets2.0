package kr.hs.emirim.sookhee.redonorpets.ui.shelter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.sookhee.redonorpets.common.setImageWithUrl
import kr.hs.emirim.sookhee.redonorpets.databinding.ItemShelterBinding
import kr.hs.emirim.sookhee.redonorpets.databinding.ItemShelterGridBinding
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Shelter

/**
 *  ShelterAdapter.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class ShelterAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Shelter> = emptyList()

    var shelterViewType = SHELTER_VIEW_TYPE.GRID
    var onItemClick: ((selectedItem: Shelter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (shelterViewType) {
            SHELTER_VIEW_TYPE.GRID -> ShelterGridViewHolder(
                ItemShelterGridBinding.inflate(inflater, parent, false),
                onItemClick
            )
            SHELTER_VIEW_TYPE.LIST -> ShelterListViewHolder(
                ItemShelterBinding.inflate(inflater, parent, false),
                onItemClick
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (shelterViewType) {
            SHELTER_VIEW_TYPE.GRID -> (holder as ShelterGridViewHolder).bind(items[position])
            SHELTER_VIEW_TYPE.LIST -> (holder as ShelterListViewHolder).bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<Shelter>) {
        items = list
        notifyDataSetChanged()
    }

    class ShelterGridViewHolder(
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

    class ShelterListViewHolder(
        private val binding: ItemShelterBinding,
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

            binding.ivShelterProfile.setImageWithUrl(item.profileImage)
        }
    }

    companion object {
        enum class SHELTER_VIEW_TYPE { GRID, LIST }
    }
}
