package kr.hs.emirim.sookhee.redonorpets.ui.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kr.hs.emirim.sookhee.redonorpets.R
import kr.hs.emirim.sookhee.redonorpets.databinding.ActivityStoryBinding

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

    private fun observeData() {
        viewModel.storyDetail.asLiveData().observe(this) {
            binding.story = it

            binding.tvLikeCount.text = getString(R.string.like_count, it.likeCount)
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
}
