package com.example.hw1calculator.calculator

import com.example.hw1calculator.utils.INCORRECT_INPUT
import com.example.hw1calculator.utils.isIntegerOrDouble
import com.example.hw1calculator.utils.isMathsSymbol
import com.example.hw1calculator.utils.removeSpaces

class Calculator {

    private val listOfMathsSymbols =
        listOf(Priority.HIGH, Priority.LOW)


    fun calculate(equation: String): String {
        var equationList =
            equation.parseToList() //parse to list of strings because we can have numbers with 2 and more symbols
        var result: String

        try {
            while (equationList.contains("(")) {
                equationList =
                    openParentheses(equationList) //count and replace inside parentheses while we have parentheses
            }
            equationList = countUp(equationList) //count when we don't have any parentheses

            result = equationList[0] //we have only one number in list after calculation
            if (result.endsWith(".0")) {
                result = result.dropLast(2) //delete .0 if we have integer
            }
        } catch (e: Exception) {
            return INCORRECT_INPUT //if we had incorrect format of input data
        }

        return if (result == "Infinity" || result == "NaN") {
            INCORRECT_INPUT //if we try to divide by zero or divide zero by zero
        } else {
            result
        }
    }

    private fun countUp(equationList: List<String>): List<String> {
        val resultList = equationList.toMutableList()
        var result: Float

        if (!equationList.isCorrectFormat()) {
            throw Exception()
        }

        listOfMathsSymbols.forEach { priority ->
            while (resultList.contains(priority.firstSign) || resultList.contains(priority.secondSign)) {
                for (i in 0 until resultList.size) {
                    if (resultList[i] == priority.firstSign || resultList[i] == priority.secondSign) {
                        val x = resultList[i - 1]
                        val y = resultList[i + 1]
                        result = when (resultList[i]) {
                            Priority.HIGH.firstSign -> x.toFloat() * y.toFloat()
                            Priority.HIGH.secondSign -> x.toFloat() / y.toFloat()
                            Priority.LOW.firstSign -> x.toFloat() + y.toFloat()
                            Priority.LOW.secondSign -> x.toFloat() - y.toFloat()
                            else -> 0F
                        }
                        resultList[i + 1] = result.toString()
                        resultList.removeAt(i - 1)
                        resultList.removeAt(i - 1)
                        break
                    }
                }
            }
        }
        return resultList
    }

    private fun openParentheses(equationList: List<String>): List<String> {
        val resultList = equationList.toMutableList()

        val startIndex = equationList.indexOfOpenParentheses() + 1
        val endIndex = equationList.indexOfFirst { it == ")" } - 1

        val insideParenthesesList = countUp(equationList.slice(startIndex..endIndex))

        for (i in startIndex..endIndex + 2) {
            resultList.removeAt(startIndex - 1)
        }

        resultList.add(startIndex - 1, insideParenthesesList[0])

        return resultList
    }

    private fun List<String>.indexOfOpenParentheses(): Int {
        var startIndex = 0
        for (i in 0 until this.size) {
            if (this[i] == "(") {
                startIndex = i
            }
            if (this[i] == ")") {
                break
            }
        }
        return startIndex
    }

    private fun List<String>.isCorrectFormat(): Boolean {
        if (this.all { it[0].isIntegerOrDouble || it[0].isMathsSymbol }) {
            for (i in 0 until this.size step 2) {
                return this[i].all { it.isIntegerOrDouble || it == '-' } && this.size % 2 != 0
            }
        }
        return false
    }

    private fun String.parseToList(): List<String> {
        val resultList = mutableListOf<String>()
        var currentString = ""

        this.forEachIndexed { index, char ->
            if (char.isIntegerOrDouble) {
                currentString += char
                if (index == this.length - 1) {
                    resultList.add(currentString)
                }
                return@forEachIndexed
            }

            if (currentString.isNotBlank()) {
                resultList.add(currentString)
                currentString = ""
            }
            resultList.add(char.toString())
        }
        return resultList.removeSpaces
    }
}


enum class Priority(val firstSign: String, val secondSign: String) {
    HIGH("*", "/"),
    LOW("+", "-"),
}
