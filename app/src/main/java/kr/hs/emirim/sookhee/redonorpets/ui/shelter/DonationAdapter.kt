package kr.hs.emirim.sookhee.redonorpets.ui.shelter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.sookhee.redonorpets.databinding.ItemDonationBinding
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Donation

/**
 *  ProductAdapter.kt
 *
 *  Created by Minji Jeong on 2022/01/19
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class DonationAdapter :
    RecyclerView.Adapter<DonationAdapter.ViewHolder>() {

    private var items: List<Donation> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemDonationBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<Donation>) {
        items = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemDonationBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Donation,
        ) {
            binding.item = item
            binding.root.setOnClickListener {
                if(item.isChecked) {
                    binding.svDonationCheck.visibility = View.GONE
                    item.isChecked = false
                } else {
                    binding.svDonationCheck.visibility = View.VISIBLE
                    item.isChecked = true
                }
            }
        }
    }
}
