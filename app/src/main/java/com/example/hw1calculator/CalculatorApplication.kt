package com.example.hw1calculator

import android.app.Application
import com.example.hw1calculator.calculator.Calculator
import com.example.hw1calculator.repository.EquationRepository

class CalculatorApplication : Application() {

    private val calculator by lazy { Calculator() }
    val repository by lazy { EquationRepository(calculator) }
}
