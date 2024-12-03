package com.teamapface.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedViewModel : ViewModel() {

    private val _savedResults = MutableLiveData<List<SavedResult>>()
    val savedResults: LiveData<List<SavedResult>> = _savedResults

    // Sample data for testing
    init {
        // Simulating data population for testing purposes
        val dummyData = listOf(
            SavedResult("Condition 1", "https://example.com/image1.jpg"),
            SavedResult("Condition 2", "https://example.com/image2.jpg")
        )
        _savedResults.value = dummyData
    }
}
