package kr.hs.emirim.sookhee.redonorpets.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kr.hs.emirim.sookhee.redonorpets.databinding.FragmentMypageBinding
import kr.hs.emirim.sookhee.redonorpets.ui.shelter.ShelterAdapter

/**
 *  MypageFragment.kt
 *
 *  Created by Minji Jeong on 2022/01/18
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MypageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MypageViewModel::class.java)

        viewModel.getMyBadgeList()
        viewModel.getMyShelterList()

        observeFlow()

        initBadgeRecyclerView()
        initShelterRecyclerView()
        setOnClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeFlow() {
        viewModel.badgeList.asLiveData().observe(viewLifecycleOwner) {
            (binding.badgeRecyclerView.adapter as BadgeAdapter).setItem(it)
        }

        viewModel.myShelterList.asLiveData().observe(viewLifecycleOwner) {
            (binding.myShelterRecyclerView.adapter as ShelterAdapter).setItem(it)
        }
    }

    private fun initBadgeRecyclerView() {
        binding.badgeRecyclerView.adapter = BadgeAdapter().apply {
            onItemClick = {

            }
        }
    }

    private fun initShelterRecyclerView() {
        binding.myShelterRecyclerView.adapter = ShelterAdapter().apply {
            shelterViewType = ShelterAdapter.Companion.SHELTER_VIEW_TYPE.LIST
            onItemClick = {

            }
        }
    }

    private fun setOnClickListener() {
        binding.btnSetting.setOnClickListener {
            // TODO : move to SettingActivity
        }

        binding.ivEditUserProfile.setOnClickListener {
            // TODO : move to EditProfileActivity
        }
    }
}
