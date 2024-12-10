package com.teamapface.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.teamapface.R
import com.teamapface.api.RetrofitClient
import com.teamapface.api.AnalysisRequest
import com.teamapface.databinding.FragmentHomeBinding
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File

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
        binding.btnCondition.setOnClickListener { setSelectedModel("Condition") }
        binding.btnType.setOnClickListener { setSelectedModel("Type") }
        binding.btnCamera.setOnClickListener { openCamera() }
        binding.btnGallery.setOnClickListener { openGallery() }
        binding.btnAnalyze.setOnClickListener {
            if (imageUri != null) {
                analyzeImage()
            } else {
                Toast.makeText(requireContext(), "Please select or capture an image first.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setSelectedModel(model: String) {
        selectedModel = model
        val colorPrimary = getThemeColor(com.google.android.material.R.attr.colorPrimary)
        val colorSecondary = getThemeColor(com.google.android.material.R.attr.colorPrimaryVariant)
        val colorOnPrimary = getThemeColor(com.google.android.material.R.attr.colorOnPrimary)

        if (model == "Condition") {
            binding.btnCondition.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), colorSecondary)
            binding.btnCondition.setTextColor(ContextCompat.getColor(requireContext(), colorOnPrimary))
            binding.btnType.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), colorPrimary)
        } else {
            binding.btnType.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), colorSecondary)
            binding.btnType.setTextColor(ContextCompat.getColor(requireContext(), colorOnPrimary))
            binding.btnCondition.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), colorPrimary)
        }
        Toast.makeText(requireContext(), "$selectedModel Model Selected", Toast.LENGTH_SHORT).show()
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    private fun analyzeImage() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val inputStream = requireContext().contentResolver.openInputStream(imageUri!!)
                val byteArray = inputStream?.readBytes() ?: throw Exception("Failed to read image")
                val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray)
                val body = MultipartBody.Part.createFormData("file", "image.jpg", requestFile)

                val apiService = RetrofitClient.instance
                val response = if (selectedModel == "Type") {
                    apiService.analyzeSkinType(body)
                } else {
                    apiService.analyzeCondition(body)
                }

                val intent = Intent(requireContext(), ResultActivity::class.java).apply {
                    putExtra("predicted_condition", response.predicted_condition)
                    putExtra("image_uri", imageUri.toString())
                    putExtra("model_type", selectedModel)
                }
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    // Get the bitmap from the camera and update the imageUri
                    val bitmap = data?.extras?.get("data") as Bitmap
                    imageUri = saveBitmapToUri(bitmap)
                    // Update the placeholder with the new image
                    binding.imagePlaceholder.setImageURI(imageUri)
                    startCrop(imageUri!!)
                }
                GALLERY_REQUEST_CODE -> {
                    // Get the URI from the gallery and update the imageUri
                    imageUri = data?.data
                    // Update the placeholder with the new image
                    binding.imagePlaceholder.setImageURI(imageUri)
                    imageUri?.let { startCrop(it) }
                }
                UCrop.REQUEST_CROP -> {
                    // Get the cropped image URI
                    val resultUri = UCrop.getOutput(data!!)
                    if (resultUri != null) {
                        imageUri = resultUri
                        // Update the placeholder with the new cropped image
                        binding.imagePlaceholder.setImageURI(imageUri)
                    }
                }
                UCrop.RESULT_ERROR -> {
                    val cropError = UCrop.getError(data!!)
                    Toast.makeText(requireContext(), "Crop error: ${cropError?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startCrop(uri: Uri) {
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped.jpg"))
        UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1000, 1000)
            .start(requireContext(), this)
    }

    private fun saveBitmapToUri(bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap,
            "CapturedImage",
            null
        )
        return Uri.parse(path)
    }

    private fun getThemeColor(attr: Int): Int {
        val typedValue = TypedValue()
        val theme = requireContext().theme
        if (theme.resolveAttribute(attr, typedValue, true)) {
            return typedValue.resourceId
        } else {
            throw IllegalArgumentException("Attribute not found in theme")
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
