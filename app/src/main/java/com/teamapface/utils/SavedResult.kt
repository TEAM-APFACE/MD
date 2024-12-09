package com.teamapface.utils.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_results")
data class SavedResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String,
    val condition: String,
    val type: String,
    val description: String
)
