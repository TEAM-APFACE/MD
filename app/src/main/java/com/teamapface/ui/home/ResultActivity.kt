package com.teamapface.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.teamapface.databinding.ActivityResultBinding
import com.teamapface.ui.saved.SavedViewModel
import com.teamapface.utils.model.SavedResult
import java.io.File

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var savedViewModel: SavedViewModel
    private var imageUri: Uri? = null
    private lateinit var predictedCondition: String
    private lateinit var modelType: String
    private var isDeletable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedViewModel = ViewModelProvider(this).get(SavedViewModel::class.java)

        // Get data from intent
        imageUri = intent.getStringExtra("image_uri")?.let { Uri.parse(it) }
        predictedCondition = intent.getStringExtra("predicted_condition") ?: "Unknown"
        modelType = intent.getStringExtra("model_type") ?: "Unknown"
        isDeletable = intent.getBooleanExtra("is_deletable", false)

        // Set UI data
        binding.imageResult.setImageURI(imageUri)
        binding.tvResultTitle.text = predictedCondition
        binding.tvResultDescription.text = modelType

        if (isDeletable) {
            binding.btnSaveResult.visibility = View.GONE
            binding.btnDeleteResult.apply {
                visibility = View.VISIBLE
                setOnClickListener { deleteResult() }
            }
        } else {
            binding.btnSaveResult.setOnClickListener { saveResult() }
            binding.btnDeleteResult.visibility = View.GONE
        }
    }

    private fun saveResult() {
        if (imageUri == null) {
            Toast.makeText(this, "No image to save!", Toast.LENGTH_SHORT).show()
            return
        }

        // Copy the image to internal storage
        val context = applicationContext
        val inputStream = contentResolver.openInputStream(imageUri!!)
        val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        file.outputStream().use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        val savedUri = file.toURI().toString()

        val result = SavedResult(
            imageUri = savedUri,
            condition = predictedCondition,
            type = modelType,
            description = binding.tvResultDescription.text.toString()
        )

        savedViewModel.addResult(result)
        Toast.makeText(this, "Result saved successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun deleteResult() {
        val result = SavedResult(
            id = intent.getIntExtra("result_id", -1),
            imageUri = imageUri.toString(),
            condition = predictedCondition,
            type = modelType,
            description = binding.tvResultDescription.text.toString()
        )

        savedViewModel.deleteResult(result)
        Toast.makeText(this, "Result deleted successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
