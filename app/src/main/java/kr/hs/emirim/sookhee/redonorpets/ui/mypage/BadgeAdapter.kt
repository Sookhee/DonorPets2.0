package kr.hs.emirim.sookhee.redonorpets.ui.mypage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.sookhee.redonorpets.databinding.ItemBadgeBinding
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Badge

/**
 *  BadgeAdapter.kt
 *
 *  Created by Minji Jeong on 2022/01/19
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class BadgeAdapter :
    RecyclerView.Adapter<BadgeAdapter.ViewHolder>() {

    private var items: List<Badge> = emptyList()
    var onItemClick: ((selectedItem: Badge) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBadgeBinding.inflate(inflater, parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<Badge>) {
        items = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemBadgeBinding,
        onItemClick: ((selectedItem: Badge) -> Unit)?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.onItemClick = onItemClick
        }

        @SuppressLint("SetTextI18n")
        fun bind(
            item: Badge,
        ) {
            binding.item = item
        }
    }
}
