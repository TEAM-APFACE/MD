package com.teamapface.ui.saved

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.teamapface.utils.database.AppDatabase
import com.teamapface.utils.model.SavedResult
import kotlinx.coroutines.launch

class SavedViewModel(application: Application) : AndroidViewModel(application) {
    private val savedResultDao = AppDatabase.getInstance(application).savedResultDao()
    val savedResults: LiveData<List<SavedResult>> = savedResultDao.getAllSavedResults()

    fun addResult(result: SavedResult) {
        viewModelScope.launch {
            savedResultDao.insert(result)
        }
    }

    fun deleteResult(result: SavedResult) {
        viewModelScope.launch {
            savedResultDao.delete(result)
        }
    }
}
