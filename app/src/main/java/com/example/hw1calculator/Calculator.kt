package com.example.hw1calculator

class Calculator {

    private val listOfMathsSymbols = listOf("*", "/", "+", "-")

    fun calculate(equation: String): String {
        var equationList = equation.parseToList() //parse to list of strings because we can have numbers with 2 and more symbols
        var result = ""

        try {
            while (equationList.contains("(")) {
                equationList = openParentheses(equationList)//count and replace inside parentheses while we have parentheses
            }
            equationList = countUp(equationList)//count when we don't have any parentheses

            result = "= ${equationList[0]}"//we have only one number in list after calculation
            if (result.endsWith(".0")) {
                result = result.dropLast(2) //delete .0 if we have integer
            }
        } catch (e: Exception) {
            return "Incorrect format. Please try again." //if we had incorrect format of input data
        }

        return if (result == "= Infinity" || result == "= NaN") {
            "Can't divide by zero. Please try again." //if we try to divide by zero or divide zero by zero
        } else {
            result
        }
    }


    private fun countUp(equationList: List<String>): List<String> {
        val resultList = equationList.toMutableList()
        var result = 0.0F

        if (!equationList.isCorrectFormat()) {
            return emptyList()
        }

        listOfMathsSymbols.forEach { mathsSymbol ->
            while (resultList.contains(mathsSymbol)) {
                for (i in 0 until resultList.size) {
                    if (resultList[i] == mathsSymbol) {
                        val x = resultList[i - 1]
                        val y = resultList[i + 1]
                        result = when (mathsSymbol) {
                            "*" -> x.toFloat() * y.toFloat()
                            "/" -> x.toFloat() / y.toFloat()
                            "+" -> x.toFloat() + y.toFloat()
                            "-" -> x.toFloat() - y.toFloat()
                            else -> 0.0F
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
        var insideParenthesesList = listOf<String>()
        var startIndex = 0
        var endIndex = 0

        startIndex = equationList.indexOfOpenParenthesis() + 1
        endIndex = equationList.indexOfFirst { it == ")" } - 1

        insideParenthesesList = equationList.slice(startIndex..endIndex)
        insideParenthesesList = countUp(insideParenthesesList)

        for (i in startIndex..endIndex + 2) {
            resultList.removeAt(startIndex - 1)
        }

        resultList.add(startIndex - 1, insideParenthesesList[0])

        return resultList
    }


    private fun List<String>.indexOfOpenParenthesis(): Int {
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
        var result = false
        if (this.all { it[0].isNumber || it[0].isMathsSymbol }) {
            for (i in 0 until this.size step 2) {
                if (this[i].all { it.isNumber || it == '-' } && this.size % 2 != 0) {
                    result = true
                } else {
                    return false
                }
            }
        }
        return result
    }


    private fun String.parseToList(): List<String> {
        val resultList = mutableListOf<String>()
        var currentString = ""
        var index = 0

        this.forEach { char ->
            index++
            if (char.isNumber) {
                currentString += char
                if (index == this.length) {
                    resultList.add(currentString)
                }
                return@forEach
            }

            if (currentString.isBlank()) {
                resultList.add(char.toString())
            } else {
                resultList.add(currentString)
                resultList.add(char.toString())
                currentString = ""
            }
        }
        return resultList.removeSpaces
    }
}