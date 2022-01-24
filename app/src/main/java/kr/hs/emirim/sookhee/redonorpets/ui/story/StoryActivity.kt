package kr.hs.emirim.sookhee.redonorpets.ui.story

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kr.hs.emirim.sookhee.redonorpets.R
import kr.hs.emirim.sookhee.redonorpets.databinding.ActivityStoryBinding
import kr.hs.emirim.sookhee.redonorpets.databinding.LayoutStoryImageBinding
import kr.hs.emirim.sookhee.redonorpets.databinding.LayoutStoryTextBinding
import kr.hs.emirim.sookhee.redonorpets.domain.entity.CONTENT_TYPE

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var viewModel: StoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(StoryViewModel::class.java)

        setContentView(binding.root)

        viewModel.getStoryDetail("FEED_ID", "SHELTER_ID")

        initView()
        observeData()
    }

    @SuppressLint("NewApi")
    private fun observeData() {
        viewModel.storyDetail.asLiveData().observe(this) {
            binding.story = it
            binding.tvLikeCount.text = getString(R.string.like_count, it.likeCount)

            it.content.forEach { content ->
                when(content.type) {
                    CONTENT_TYPE.TEXT -> addTextView(content.content)
                    CONTENT_TYPE.IMAGE -> addImageView(content.content)
                }
            }
        }

        viewModel.commentList.asLiveData().observe(this) {
            (binding.commentRecyclerView.adapter as CommentAdapter).setItem(it)
        }
    }

    private fun initView() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnLike.setOnClickListener {
            // TODO: toggle story like
        }

        binding.btnAddComment.setOnClickListener {
            // TODO: add comment
        }

        binding.commentRecyclerView.adapter = CommentAdapter()
        // TODO: set comment layout (user profile)

        // TODO: set content container
    }

    private fun addImageView(content: String) {
        binding.storyContentContainer.addView(
            LayoutStoryImageBinding.inflate(
                LayoutInflater.from(this),
                binding.storyContentContainer,
                false
            ).apply {
                this.ivContent.setBackgroundColor(Color.parseColor(content))
            }.root
        )
    }

    private fun addTextView(content: String) {
        binding.storyContentContainer.addView(
            LayoutStoryTextBinding.inflate(
                LayoutInflater.from(this),
                binding.storyContentContainer,
                false
            ).apply {
                this.tvContent.text = content
            }.root
        )
    }
}
