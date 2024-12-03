package com.teamapface.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.teamapface.databinding.ActivitySplashBinding
import com.teamapface.ui.description.DescriptionActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.postDelayed({
            startActivity(Intent(this, DescriptionActivity::class.java))
            finish()
        }, 2000) // 2-second delay
    }
}
