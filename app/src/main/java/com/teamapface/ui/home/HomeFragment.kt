package com.teamapface.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.teamapface.R
import com.teamapface.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var selectedModel: String = "Condition"
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        // Button selection logic
        binding.btnCondition.setOnClickListener {
            setSelectedModel("Condition")
        }

        binding.btnType.setOnClickListener {
            setSelectedModel("Type")
        }

        // Camera Button
        binding.btnCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        }

        // Gallery Button
        binding.btnGallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }

        // Analyze Button
        binding.btnAnalyze.setOnClickListener {
            if (imageUri != null) {
                Toast.makeText(requireContext(), "Analyzing image with $selectedModel model...", Toast.LENGTH_SHORT).show()
                analyzeImage()
            } else {
                Toast.makeText(requireContext(), "Please select or capture an image first.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setSelectedModel(model: String) {
        selectedModel = model

        // Get colors from the current theme
        val colorPrimary = getThemeColor(com.google.android.material.R.attr.colorPrimary)
        val colorSecondary = getThemeColor(com.google.android.material.R.attr.colorPrimaryVariant)
        val colorOnPrimary = getThemeColor(com.google.android.material.R.attr.colorOnPrimary)

        if (model == "Condition") {
            // Highlight Condition button
            binding.btnCondition.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), colorSecondary)
            binding.btnCondition.setTextColor(ContextCompat.getColor(requireContext(), colorOnPrimary))

            // Reset Type button
            binding.btnType.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), colorPrimary)
            binding.btnType.setTextColor(ContextCompat.getColor(requireContext(), colorOnPrimary))
        } else {
            // Highlight Type button
            binding.btnType.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), colorSecondary)
            binding.btnType.setTextColor(ContextCompat.getColor(requireContext(), colorOnPrimary))

            // Reset Condition button
            binding.btnCondition.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), colorPrimary)
            binding.btnCondition.setTextColor(ContextCompat.getColor(requireContext(), colorOnPrimary))
        }

        Toast.makeText(requireContext(), "$selectedModel Model Selected", Toast.LENGTH_SHORT).show()
    }

    // Utility method to fetch theme color
    private fun getThemeColor(attr: Int): Int {
        val typedValue = TypedValue()
        val theme = requireContext().theme
        if (theme.resolveAttribute(attr, typedValue, true)) {
            return typedValue.resourceId // Get the resource ID of the color
        } else {
            throw IllegalArgumentException("Attribute not found in theme")
        }
    }

    private fun analyzeImage() {
        if (imageUri != null) {
            val intent = Intent(requireContext(), ResultActivity::class.java)
            intent.putExtra("image_uri", imageUri.toString())
            intent.putExtra("result_title", "Condition: Sample Condition")
            intent.putExtra("result_description", "This is a placeholder description for the condition.")
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Please select or capture an image first.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    binding.imagePlaceholder.setImageBitmap(bitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    imageUri = data?.data
                    imageUri?.let {
                        val inputStream = requireContext().contentResolver.openInputStream(it)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.imagePlaceholder.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 100
        private const val GALLERY_REQUEST_CODE = 101
    }
}
