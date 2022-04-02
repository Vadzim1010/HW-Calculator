package com.example.hw1calculator

val Char.isNumber
    get() = when (this) {
        '0' -> true
        '1' -> true
        '2' -> true
        '3' -> true
        '4' -> true
        '5' -> true
        '6' -> true
        '7' -> true
        '8' -> true
        '9' -> true
        '.' -> true
        else -> false
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
    get() = this.filter { it != " " }

