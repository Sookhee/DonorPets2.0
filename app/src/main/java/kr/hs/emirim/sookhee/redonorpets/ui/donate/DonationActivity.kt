package kr.hs.emirim.sookhee.redonorpets.ui.donate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.hs.emirim.sookhee.redonorpets.R

class DonationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)

        val list = intent.getSerializableExtra("DONATION_LIST")
    }
}