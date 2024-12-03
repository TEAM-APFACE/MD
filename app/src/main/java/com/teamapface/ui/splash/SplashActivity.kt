package com.teamapface.ui.splash

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.teamapface.databinding.ActivitySplashBinding
import com.teamapface.ui.description.DescriptionActivity
import com.teamapface.utils.ThemeUtils

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply the saved theme before initializing the layout
        ThemeUtils.applyTheme(this)
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.postDelayed({
            startActivity(Intent(this, DescriptionActivity::class.java))
            finish()
        }, 2000) // 2-second delay
    }
}
