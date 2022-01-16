package kr.hs.emirim.sookhee.redonorpets.ui.shelter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kr.hs.emirim.sookhee.redonorpets.databinding.ActivityShelterProfileBinding

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
    }

    private fun observeData() {
        viewModel.shelterData.asLiveData().observe(this) {
            binding.shelter = it
        }
    }
}
