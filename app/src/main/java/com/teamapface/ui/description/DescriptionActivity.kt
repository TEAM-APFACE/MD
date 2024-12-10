package com.teamapface.ui.description

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.teamapface.MainActivity
import com.teamapface.R
import com.teamapface.databinding.ActivityDescriptionBinding
import com.teamapface.utils.ThemeUtils

class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply the saved theme before initializing the layout
        ThemeUtils.applyTheme(this)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initially, set all the views to be invisible
        resetViewsForAnimation()

        // Set the initial background color to the value from XML (@color/blue_200)
        val initialBackgroundColor = ContextCompat.getColor(this, R.color.blue_200)
        binding.root.setBackgroundColor(initialBackgroundColor)

        // Animate the ImageView (logo) with text and the CardView & Button at the same time
        val fadeInDuration = 500L // Faster fade-in duration for both image and cards

        // Animate the ImageView (logo) to fade in
        binding.root.postDelayed({
            ObjectAnimator.ofFloat(binding.imgLogo, "alpha", 0f, 1f).apply {
                duration = fadeInDuration // Image fade-in duration
                start()
            }

            // Animate the CardView with text (at the same time as the ImageView)
            ObjectAnimator.ofFloat(binding.cardview, "alpha", 0f, 1f).apply {
                duration = fadeInDuration // CardView fade-in duration
                start()
            }

            // Animate the Button with text (at the same time as the ImageView and CardView)
            ObjectAnimator.ofFloat(binding.btnStart, "alpha", 0f, 1f).apply {
                duration = fadeInDuration // Button fade-in duration
                start()
            }
        }, 0) // All animations start immediately

        // Animate the background color change of the root layout (quick transition)
        binding.root.postDelayed({
            val colorAnim = ValueAnimator.ofArgb(initialBackgroundColor, Color.parseColor("#2196F3")) // Blue color
            colorAnim.duration = 800 // Shortened the duration for the background color change
            colorAnim.addUpdateListener { animator ->
                binding.root.setBackgroundColor(animator.animatedValue as Int)
            }
            colorAnim.start()
        }, 500) // Start background color change after the fade-in animations

        // Set up the "Start" button to navigate to MainActivity
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish() // Finish DescriptionActivity so it is removed from the stack
        }
    }

    // Helper function to reset alpha values to ensure animations run
    private fun resetViewsForAnimation() {
        binding.imgLogo.alpha = 0f
        binding.cardview.alpha = 0f
        binding.btnStart.alpha = 0f
        binding.root.setBackgroundColor(Color.WHITE) // Set initial background color
    }
}
