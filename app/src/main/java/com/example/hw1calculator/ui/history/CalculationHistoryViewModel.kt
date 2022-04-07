package com.example.hw1calculator.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hw1calculator.model.Equation
import com.example.hw1calculator.repository.EquationRepository

class EquationHistoryViewModel(
    application: Application,
    private val repository: EquationRepository,
) : AndroidViewModel(application) {

    private val history = repository.getHistory()

    fun getHistory(): LiveData<List<Equation>> {
        return history
    }

    fun addSymbol(chars: String) {
        chars.forEach { char ->
            repository.addSymbol(char)
        }
    }
}


class EquationHistoryViewModelFactory(
    private val application: Application,
    private val repository: EquationRepository,
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EquationHistoryViewModel(application, repository) as T
    }
}
