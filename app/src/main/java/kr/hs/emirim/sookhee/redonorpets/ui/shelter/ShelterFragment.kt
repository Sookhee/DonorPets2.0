package kr.hs.emirim.sookhee.redonorpets.ui.shelter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kr.hs.emirim.sookhee.redonorpets.databinding.FragmentShelterBinding
import kr.hs.emirim.sookhee.redonorpets.domain.entity.Shelter
import kr.hs.emirim.sookhee.redonorpets.ui.UiState

/**
 *  ShelterFragment.kt
 *
 *  Created by Minji Jeong on 2022/01/12
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

@AndroidEntryPoint
class ShelterFragment : Fragment() {
    private var _binding: FragmentShelterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ShelterViewModel
    private var shelterList: List<Shelter> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShelterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ShelterViewModel::class.java)

        viewModel.getShelterList()

        observeFlow()

        initTabLayout()
        initRecyclerView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeFlow() {
        viewModel.shelterState.asLiveData().observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    // TODO : Start Loading Bar
                }
                is UiState.Success<*> -> {
                    shelterList = uiState.data as List<Shelter>
                    filterShelterItem()
                }
                is UiState.Error -> {
                    // TODO : Error Dialog
                }
            }
        }
    }

    private fun initTabLayout() {
        binding.shelterFilterTabLayout.apply {
            addTab(newTab().setText("전체"))
            addTab(newTab().setText("서울"))
            addTab(newTab().setText("경기도"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        1 -> filterShelterItem("서울")
                        2 -> filterShelterItem("경기도")
                        else -> filterShelterItem()
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun filterShelterItem(area: String? = null) {
        (binding.shelterRecyclerView.adapter as ShelterAdapter).setItem(shelterList.filter { it.area == area || area == null })
    }

    private fun initRecyclerView() {
        binding.shelterRecyclerView.adapter = ShelterAdapter().apply {
            onItemClick = {
                val intent = Intent(context, ShelterProfileActivity::class.java)
                intent.putExtra("SHELTER_ID", it.id)

                startActivity(intent)
            }
        }
    }
}
