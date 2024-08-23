package com.example.calculator.model

import net.objecthunter.exp4j.ExpressionBuilder
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorModel {

    fun evaluateExpression(expression: String): Double {
        return try {
            val formattedExpression = expression.replace('x', '*')
            val result = eval(formattedExpression)
            roundToEightDecimalPlaces(result)
        } catch (e: Exception) {
            Double.NaN
        }
    }

    private fun eval(expression: String): Double {
        return ExpressionBuilder(expression).build().evaluate()
    }

    private fun roundToEightDecimalPlaces(value: Double): Double {
        return BigDecimal(value).setScale(8, RoundingMode.HALF_UP).toDouble()
    }
}