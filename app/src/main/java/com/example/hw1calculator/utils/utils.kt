package com.example.hw1calculator.utils

val Char.isIntegerOrDouble
    get() = this.isDigit() || this == '.' //because we can have a double

fun Char.isDigitOrParenthesis(): Boolean {
    return this.isDigit() || this == ')' //')' because we need to add "*" if equation format is (x+y)(x+y)
}

val Char.isMathsSymbol
    get() = when (this) {
        '*' -> true
        '/' -> true
        '+' -> true
        '-' -> true
        else -> false
    }

val List<String>.removeSpaces
    get() = this.filter { it.isNotBlank() }
