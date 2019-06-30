package com.app.mmse.numbers.serial_threes

import java.lang.Double.parseDouble

class NumericUtils {
    //This function checks whether a String is Numeric or not!
    fun isNumeric(stringToBeChecked: String): Boolean {
        var numeric = true

        try {
            parseDouble(stringToBeChecked)
        } catch (e: NumberFormatException) {
            numeric = false
        }

        when {
            numeric -> println("$stringToBeChecked is a number")
            else -> println("$stringToBeChecked is not a number")
        }

        return numeric
    }
}