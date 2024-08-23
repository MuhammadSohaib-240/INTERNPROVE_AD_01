package com.example.calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.model.CalculatorModel

class CalculatorViewModel : ViewModel() {
    private val _displayText = MutableLiveData<String>("0")
    private val _historyText = MutableLiveData<String>("")

    val displayText: LiveData<String> get() = _displayText
    val historyText: LiveData<String> get() = _historyText

    private val calculatorModel = CalculatorModel()
    private var currentInput = ""

    fun onButtonClick(buttonText: String) {
        when (buttonText) {
            "=" -> calculateResult()
            "Clear" -> clearInput()
            "<" -> delete()
            else -> appendInput(buttonText)
        }
    }

    private fun appendInput(text: String) {
        currentInput += text
        _displayText.value = currentInput
    }

    private fun calculateResult() {
        val result = calculatorModel.evaluateExpression(currentInput)
        if (result.isNaN()) {
            _displayText.value = "Error"
            _historyText.value = ""
        }
        _historyText.value = currentInput
        currentInput = result.toString() + ""
        _displayText.value = result.toString()
    }

    private fun clearInput() {
        currentInput = ""
        _historyText.value = ""
        _displayText.value = "0"
    }

    private fun delete() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            _displayText.value = currentInput.ifEmpty { "0" }
        }
    }
}