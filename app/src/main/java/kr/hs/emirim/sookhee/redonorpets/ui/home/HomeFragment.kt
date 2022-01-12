package kr.hs.emirim.sookhee.redonorpets.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.android.material.tabs.TabLayout
import kr.hs.emirim.sookhee.redonorpets.databinding.FragmentHomeBinding
import kr.hs.emirim.sookhee.redonorpets.domain.entity.StoryFeed
import kr.hs.emirim.sookhee.redonorpets.ui.UiState

/**
 *  HomeFragment.kt
 *
 *  Created by Minji Jeong on 2022/01/06
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private var storyList: List<StoryFeed> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getStoryList()
        observeFlow()

        initTabLayout()
        initRecyclerView()
        setOnClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeFlow() {
        viewModel.storyState.asLiveData().observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    // TODO : Start Loading Bar
                }
                is UiState.Success<*> -> {
                    storyList = uiState.data as List<StoryFeed>
                    filterStoryItem()
                }
                is UiState.Error -> {
                    // TODO : Error Dialog
                }
            }
        }
    }

    private fun initTabLayout() {
        binding.storyFilterTabLayout.apply {
            addTab(newTab().setText("전체"))
            addTab(newTab().setText("서울"))
            addTab(newTab().setText("경기도"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        1 -> filterStoryItem("서울")
                        2 -> filterStoryItem("경기도")
                        else -> filterStoryItem()
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun filterStoryItem(area: String? = null) {
        (binding.storyRecyclerView.adapter as StoryFeedAdapter).setItem(storyList.filter { it.shelterArea == area || area == null })
    }

    private fun initRecyclerView() {
        binding.storyRecyclerView.adapter = StoryFeedAdapter().apply {
            onItemClick = {
                // TODO : move to StoryDetailActivity
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnNotification.setOnClickListener {
            // TODO : move to NotificationActivity
        }
    }
}
