package com.example.hw1calculator.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hw1calculator.calculator.Calculator
import com.example.hw1calculator.model.Equation
import com.example.hw1calculator.utils.CalculationStatus
import com.example.hw1calculator.utils.INCORRECT_INPUT
import com.example.hw1calculator.utils.isDigitOrParenthesis

class EquationRepository(private val calculator: Calculator) {

    private val equation = MutableLiveData("")
    private val result = MutableLiveData("")
    private val history = mutableListOf<Equation>()
    private var isCalculated = false
    private var id = 0


    fun getEquationLiveData(): LiveData<String> {
        return equation
    }

    fun getResultLiveData(): LiveData<String> {
        return result
    }

    fun getHistory(): LiveData<List<Equation>> {
        return MutableLiveData(history)
    }

    fun calculate(): CalculationStatus {
        return when {
            getResult() == INCORRECT_INPUT -> CalculationStatus.FAILED
            else -> {
                history.add(Equation(
                    id = id++,
                    body = equation.value.toString(),
                    result = getResult()))
                equation.value = "= ${getResult()}" //add result to equation live data
                clearResult() //delete result from result live data
                isCalculated = true //clear equation when any next button is pressed afterwards
                CalculationStatus.SUCCESS
            }
        }
    }

    fun addSymbol(symbol: Char) {
        clearIfCalculated { isCalculated } //clear equation when any button is clicked after equation was calculated

        if (symbol == '(' && equation.value?.lastOrNull()?.isDigitOrParenthesis() == true) {
            equation.value += "*$symbol" //add "*" to equation if we open parentheses and it was no sign before
        } else {
            equation.value += symbol  //add symbol if it usual case
        }
        updateResult()
    }

    fun backspace() {
        equation.value = equation.value?.dropLast(1)

        if (isCalculated) {
            clearEquation() //don't need backspace if equation was calculated
        }
        updateResult()
    }

    fun clearAll() {
        clearEquation()
        clearResult()
    }

    private fun clearEquation() {
        equation.value = ""
    }

    private fun clearResult() {
        result.value = ""
    }

    private fun clearIfCalculated(predicate: () -> Boolean) {
        if (predicate()) {
            clearEquation()
            isCalculated = false
        }
    }

    private fun getResult(): String {
        return calculator.calculate(equation.value.toString())
    }

    private fun updateResult() {
        when {
            getResult() == INCORRECT_INPUT -> clearResult() //clear result if input is incorrect
            else -> result.value =
                "= ${getResult()}" //add result in result live data if calculation is successful
        }
    }
}
