package com.teamapface.utils.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.teamapface.utils.model.SavedResult

@Dao
interface SavedResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: SavedResult)

    @Delete
    suspend fun delete(result: SavedResult)

    @Query("SELECT * FROM saved_results")
    fun getAllSavedResults(): LiveData<List<SavedResult>>
}
