package com.teamapface.ui.description

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamapface.MainActivity
import com.teamapface.databinding.ActivityDescriptionBinding
import com.teamapface.utils.ThemeUtils

class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply the saved theme before initializing the layout
        ThemeUtils.applyTheme(this)
        super.onCreate(savedInstanceState)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
