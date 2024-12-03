package com.teamapface.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamapface.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private lateinit var savedResultsAdapter: SavedResultsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val savedViewModel =
            ViewModelProvider(this).get(SavedViewModel::class.java)

        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize RecyclerView and set the layout manager
        val recyclerView: RecyclerView = binding.recyclerViewSavedResults
        recyclerView.layoutManager = LinearLayoutManager(context) // Attach the layout manager here

        savedResultsAdapter = SavedResultsAdapter(emptyList()) { savedResult ->
            // Handle item click
        }
        recyclerView.adapter = savedResultsAdapter

        // Observe saved results and update the adapter
        savedViewModel.savedResults.observe(viewLifecycleOwner) { results: List<SavedResult> ->
            savedResultsAdapter = SavedResultsAdapter(results) { savedResult ->
                // Handle item click
            }
            recyclerView.adapter = savedResultsAdapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
