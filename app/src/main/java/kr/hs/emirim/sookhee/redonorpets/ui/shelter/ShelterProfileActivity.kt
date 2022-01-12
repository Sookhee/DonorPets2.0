package kr.hs.emirim.sookhee.redonorpets.ui.shelter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.hs.emirim.sookhee.redonorpets.databinding.ActivityShelterProfileBinding

class ShelterProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShelterProfileBinding
    private lateinit var viewModel: ShelterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShelterProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ShelterViewModel::class.java)

        setContentView(binding.root)
    }
}