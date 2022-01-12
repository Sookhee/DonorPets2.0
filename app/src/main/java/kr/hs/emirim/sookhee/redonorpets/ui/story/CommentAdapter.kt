package kr.hs.emirim.sookhee.redonorpets.ui.story

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.sookhee.redonorpets.common.setImageWithUrl
import kr.hs.emirim.sookhee.redonorpets.databinding.ItemCommentBinding
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Comment

/**
 *  CommentAdapter.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class CommentAdapter :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private var items: List<Comment> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCommentBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(list: List<Comment>) {
        items = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemCommentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment, ) {
            binding.comment = comment
            binding.ivProfile.setImageWithUrl(comment.profileImage)
        }
    }
}
