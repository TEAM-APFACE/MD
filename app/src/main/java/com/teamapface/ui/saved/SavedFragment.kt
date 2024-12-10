package com.teamapface.ui.saved

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamapface.databinding.FragmentSavedBinding
import com.teamapface.ui.home.ResultActivity
import com.teamapface.utils.model.SavedResult

class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private lateinit var savedResultsAdapter: SavedResultsAdapter
    private lateinit var savedViewModel: SavedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        savedViewModel = ViewModelProvider(requireActivity()).get(SavedViewModel::class.java)

        setupRecyclerView()
        observeSavedResults()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSavedResults.layoutManager = LinearLayoutManager(context)
        savedResultsAdapter = SavedResultsAdapter(emptyList()) { result ->
            openResultDetail(result)
        }
        binding.recyclerViewSavedResults.adapter = savedResultsAdapter
    }

    private fun observeSavedResults() {
        savedViewModel.savedResults.observe(viewLifecycleOwner) { results ->
            // Hide the loading indicator
            binding.progressBarLoading.visibility = View.GONE

            // Check if there are saved results
            if (results.isNullOrEmpty()) {
                binding.recyclerViewSavedResults.visibility = View.GONE
                binding.textViewNoSavedItems.visibility = View.VISIBLE
            } else {
                binding.recyclerViewSavedResults.visibility = View.VISIBLE
                binding.textViewNoSavedItems.visibility = View.GONE
                savedResultsAdapter.updateResults(results)
            }
        }

        // Simulate loading state initially
        binding.progressBarLoading.visibility = View.VISIBLE
        binding.recyclerViewSavedResults.visibility = View.GONE
        binding.textViewNoSavedItems.visibility = View.GONE
    }

    private fun openResultDetail(result: SavedResult) {
        val intent = Intent(requireContext(), ResultActivity::class.java).apply {
            putExtra("result_id", result.id)
            putExtra("image_uri", result.imageUri)
            putExtra("predicted_condition", result.condition)
            putExtra("model_type", result.type)
            putExtra("is_deletable", true)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
