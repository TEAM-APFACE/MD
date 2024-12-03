package com.teamapface.ui.home

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teamapface.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the passed image URI and display it
        val imageUri = intent.getStringExtra("image_uri")?.let { Uri.parse(it) }
        imageUri?.let {
            binding.imageResult.setImageURI(it)
        }

        // Retrieve and display the result title
        val resultTitle = intent.getStringExtra("result_title")
        binding.tvResultTitle.text = resultTitle

        // Retrieve and display the result description
        val resultDescription = intent.getStringExtra("result_description")
        binding.tvResultDescription.text = resultDescription

        // Placeholder save button listener
        binding.btnSaveResult.setOnClickListener {
            saveResult()
        }
    }

    private fun saveResult() {
        // Placeholder for save logic
        Toast.makeText(this, "Save feature not implemented yet.", Toast.LENGTH_SHORT).show()
    }
}
