package com.example.navaltobiscalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private lateinit var buttonAC: Button
    private lateinit var buttonPlusMinus: Button
    private lateinit var buttonPercent: Button
    private lateinit var buttonDivide: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonPlus: Button
    private lateinit var buttonEquals: Button
    private lateinit var button0: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var buttonDot: Button

    private var currentInput = ""
    private var previousInput = ""
    private var operator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)
        buttonAC = findViewById(R.id.buttonAC)
        buttonPlusMinus = findViewById(R.id.buttonPlusMinus)
        buttonPercent = findViewById(R.id.buttonPercent)
        buttonDivide = findViewById(R.id.buttonDivide)
        buttonMultiply = findViewById(R.id.buttonMultiply)
        buttonMinus = findViewById(R.id.buttonMinus)
        buttonPlus = findViewById(R.id.buttonPlus)
        buttonEquals = findViewById(R.id.buttonEquals)
        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        buttonDot = findViewById(R.id.buttonDot)

        setNumberListeners()
        setOperatorListeners()
        setSpecialFunctionListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setNumberListeners() {
        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        for (button in numberButtons) {
            button.setOnClickListener {
                val value = (it as Button).text.toString()
                currentInput += value

                if (operator.isNotEmpty()) {
                    tvDisplay.text = "$previousInput $operator $currentInput"
                } else {
                    tvDisplay.text = currentInput
                }
            }
        }

        buttonDot.setOnClickListener {
            if (!currentInput.contains(".")) {
                currentInput += "."
                if (operator.isNotEmpty()) {
                    tvDisplay.text = "$previousInput $operator $currentInput"
                } else {
                    tvDisplay.text = currentInput
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setOperatorListeners() {
        val operatorButtons = listOf(buttonPlus, buttonMinus, buttonMultiply, buttonDivide)
        for (button in operatorButtons) {
            button.setOnClickListener {
                if (currentInput.isNotEmpty()) {
                    previousInput = currentInput
                    operator = (it as Button).text.toString()
                    currentInput = ""
                    tvDisplay.text = "$previousInput $operator"
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSpecialFunctionListeners() {
        buttonPlusMinus.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = if (currentInput.startsWith("-")) {
                    currentInput.substring(1)
                } else {
                    "-$currentInput"
                }
                tvDisplay.text = if (operator.isNotEmpty()) {
                    "$previousInput $operator $currentInput"
                } else {
                    currentInput
                }
            }
        }
        buttonPercent.setOnClickListener {
            if (currentInput.isNotEmpty()) {
                val percentage = (currentInput.toDoubleOrNull() ?: 0.0) / 100
                currentInput = percentage.toString()
                tvDisplay.text = if (operator.isNotEmpty()) {
                    "$previousInput $operator $currentInput"
                } else {
                    currentInput
                }
            }
        }
        buttonAC.setOnClickListener {
            currentInput = ""
            previousInput = ""
            operator = ""
            tvDisplay.text = "0"
        }

        buttonEquals.setOnClickListener {
            if (currentInput.isNotEmpty() && previousInput.isNotEmpty()) {
                val result = when (operator) {
                    "+" -> previousInput.toDouble() + currentInput.toDouble()
                    "-" -> previousInput.toDouble() - currentInput.toDouble()
                    "×" -> previousInput.toDouble() * currentInput.toDouble()
                    "÷" -> previousInput.toDouble() / currentInput.toDouble()
                    else -> return@setOnClickListener
                }
                tvDisplay.text = result.toString()
                currentInput = result.toString()
                previousInput = ""
                operator = ""
            }
        }
    }
}