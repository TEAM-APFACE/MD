package com.teamapface.ui.saved

data class SavedResult(
    val condition: String, // The condition description
    val imageUri: String   // The URI of the saved image (can be a URL or local URI)
)
