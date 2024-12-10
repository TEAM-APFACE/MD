package com.teamapface.ui.splash

import android.animation.ObjectAnimator
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
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set initial alpha values to 0f for both views (invisible initially)
        resetViewsForAnimation()

        // Fade-in animation for the CardView
        ObjectAnimator.ofFloat(binding.cardview, "alpha", 0f, 1f).apply {
            duration = 2000 // 2 seconds
            start()
        }

        // Fade-in animation for the ImageView (logo)
        ObjectAnimator.ofFloat(binding.imgLogo, "alpha", 0f, 1f).apply {
            duration = 2000 // 3 seconds
            startDelay = 2000 // Start after the CardView animation
            start()
        }

        // Delay before transitioning to the next activity
        binding.root.postDelayed({
            startActivity(Intent(this, DescriptionActivity::class.java))
            finish()
        }, 2000) // 3-second delay before transitioning to the next screen
    }

    override fun onResume() {
        super.onResume()

        // Reset alpha values when the activity is resumed
        resetViewsForAnimation()

        // Re-trigger animations if the activity was resumed from the background
        ObjectAnimator.ofFloat(binding.cardview, "alpha", 0f, 1f).apply {
            duration = 2000 // 2 seconds
            start()
        }

        ObjectAnimator.ofFloat(binding.imgLogo, "alpha", 0f, 1f).apply {
            duration = 3000 // 3 seconds
            startDelay = 2000 // Start after the CardView animation
            start()
        }
    }

    // Helper function to reset alpha values to ensure animations run
    private fun resetViewsForAnimation() {
        binding.cardview.alpha = 0f
        binding.imgLogo.alpha = 0f
    }
}
