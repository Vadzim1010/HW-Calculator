package com.example.hw1calculator.ui.calculator

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hw1calculator.repository.EquationRepository
import com.example.hw1calculator.utils.CalculationStatus
import com.example.hw1calculator.utils.INCORRECT_INPUT

class CalculatorViewModel(
    application: Application,
    private val repository: EquationRepository,
) : AndroidViewModel(application) {

    private val equation = repository.getEquationLiveData()
    private val result = repository.getResultLiveData()

    fun getEquation(): LiveData<String> {
        return equation
    }

    fun getResult(): LiveData<String> {
        return result
    }

    fun addSymbol(symbol: Char) {
        repository.addSymbol(symbol)
    }

    fun backspace() {
        repository.backspace()
    }

    fun calculate() {
        when (repository.calculate()) {
            CalculationStatus.SUCCESS -> return //do nothing because we update equation by live data
            CalculationStatus.FAILED -> getToast()
        }
    }

    fun clearAll() {
        repository.clearAll()
    }

    private fun getToast() {
        Toast.makeText(getApplication(), INCORRECT_INPUT, Toast.LENGTH_SHORT).show()
    }
}


class CalculatorViewModelFactory(
    private val application: Application,
    private val repository: EquationRepository,
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalculatorViewModel(application, repository) as T
    }
}
