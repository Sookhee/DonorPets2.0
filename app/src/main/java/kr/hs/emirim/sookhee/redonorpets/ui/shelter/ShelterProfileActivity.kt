package kr.hs.emirim.sookhee.redonorpets.ui.shelter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kr.hs.emirim.sookhee.redonorpets.databinding.ActivityShelterProfileBinding
import kr.hs.emirim.sookhee.redonorpets.ui.donate.DonationActivity
import kr.hs.emirim.sookhee.redonorpets.ui.home.StoryFeedAdapter
import kr.hs.emirim.sookhee.redonorpets.ui.story.StoryActivity

class ShelterProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShelterProfileBinding
    private lateinit var viewModel: ShelterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShelterProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ShelterViewModel::class.java)

        setContentView(binding.root)

        viewModel.getShelterData("")
        observeData()

        initDonationRecyclerView()
        initStoryRecyclerView()
        setOnClickListener()
    }

    private fun observeData() {
        viewModel.shelterData.asLiveData().observe(this) {
            binding.shelter = it
            (binding.donationRecyclerView.adapter as DonationAdapter).setItem(it.productList)
        }

        viewModel.storyList.asLiveData().observe(this) {
            (binding.storyRecyclerView.adapter as StoryFeedAdapter).setItem(it)
        }
    }

    private fun initDonationRecyclerView() {
        binding.donationRecyclerView.adapter = DonationAdapter()
    }

    private fun initStoryRecyclerView() {
        binding.storyRecyclerView.adapter = StoryFeedAdapter().apply {
            onItemClick = {
                val intent = Intent(this@ShelterProfileActivity, StoryActivity::class.java)

                startActivity(intent)
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnLike.setOnClickListener {

        }

        binding.btnDonate.setOnClickListener {
            val intent = Intent(this, DonationActivity::class.java)
            startActivity(intent)
        }
    }
}
