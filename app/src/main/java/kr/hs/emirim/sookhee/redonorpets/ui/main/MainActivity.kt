package kr.hs.emirim.sookhee.redonorpets.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kr.hs.emirim.sookhee.redonorpets.*
import kr.hs.emirim.sookhee.redonorpets.databinding.ActivityMainBinding
import kr.hs.emirim.sookhee.redonorpets.ui.home.HomeFragment

/**
 *  MainActivity.kt
 *
 *  Created by Minji Jeong on 2022/01/06
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val shelterFragment by lazy { FragmentShelter() }
    private val homeFragment by lazy { HomeFragment() }
    private val mypageFragment by lazy { FragmentMypage() }
    private var activeFragment = homeFragment as Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        checkIsLogin()
    }

    private fun checkIsLogin() {
        val isLogin = true // TODO : 파베 연결하기 전 임시 플래그

        if (isLogin) {
            initBottomNavigation()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun initBottomNavigation() {
        binding.navigationView.apply {
            selectedItemId = R.id.homeItem
            setOnNavigationItemSelectedListener { menu ->
                when (menu.itemId) {
                    R.id.shelterItem -> setFragment(shelterFragment)
                    R.id.homeItem -> setFragment(homeFragment)
                    R.id.mypageItem -> setFragment(mypageFragment)
                }

                return@setOnNavigationItemSelectedListener true
            }
            setFragment(homeFragment)
        }
    }

    private fun setFragment(currentFragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(activeFragment)

        if (!currentFragment.isAdded) {
            fragmentManager.add(R.id.frameLayout, currentFragment, currentFragment.toString())
                .show(currentFragment).commit()
        } else if (activeFragment != currentFragment) {
            fragmentManager.show(currentFragment).commit()
        }

        activeFragment = currentFragment
    }
}
