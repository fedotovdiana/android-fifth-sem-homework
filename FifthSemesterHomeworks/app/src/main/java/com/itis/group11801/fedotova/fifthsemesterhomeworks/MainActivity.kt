package com.itis.group11801.fedotova.fifthsemesterhomeworks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var resultNumber = 0
    private var editedNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpChangeTheme()
        setUpCalculator()
    }

    private fun setUpChangeTheme() {
        changeThemeButton.setOnClickListener {
            when (AppCompatDelegate.getDefaultNightMode()) {
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    private fun setUpCalculator() {
        numberOneButton.setOnClickListener { updateResultTextView(Calculator.ONE) }
        numberTwoButton.setOnClickListener { updateResultTextView(Calculator.TWO) }
        numberThreeButton.setOnClickListener { updateResultTextView(Calculator.THREE) }
        numberFourButton.setOnClickListener { updateResultTextView(Calculator.FOUR) }
        numberFiveButton.setOnClickListener { updateResultTextView(Calculator.FIVE) }
        numberSixButton.setOnClickListener { updateResultTextView(Calculator.SIX) }
        numberSevenButton.setOnClickListener { updateResultTextView(Calculator.SEVEN) }
        numberEightButton.setOnClickListener { updateResultTextView(Calculator.EIGHT) }
        numberNineButton.setOnClickListener { updateResultTextView(Calculator.NINE) }
        numberZeroButton.setOnClickListener { updateResultTextView(Calculator.ZERO) }
        clearButton.setOnClickListener { updateResultTextView(Calculator.SUM) }
        sumButton.setOnClickListener { updateResultTextView(Calculator.CLEAR) }
    }

    private fun updateResultTextView(operation: Calculator) {
        resultTextView.text = when (operation) {
            Calculator.ONE -> {
                editedNumber = editedNumber * 10 + 1
                "$editedNumber"
            }
            Calculator.TWO -> {
                editedNumber = editedNumber * 10 + 2
                "$editedNumber"
            }
            Calculator.THREE -> {
                editedNumber = editedNumber * 10 + 3
                "$editedNumber"
            }
            Calculator.FOUR -> {
                editedNumber = editedNumber * 10 + 4
                "$editedNumber"
            }
            Calculator.FIVE -> {
                editedNumber = editedNumber * 10 + 5
                "$editedNumber"
            }
            Calculator.SIX -> {
                editedNumber = editedNumber * 10 + 6
                "$editedNumber"
            }
            Calculator.SEVEN -> {
                editedNumber = editedNumber * 10 + 7
                "$editedNumber"
            }
            Calculator.EIGHT -> {
                editedNumber = editedNumber * 10 + 8
                "$editedNumber"
            }
            Calculator.NINE -> {
                editedNumber = editedNumber * 10 + 9
                "$editedNumber"
            }
            Calculator.ZERO -> {
                editedNumber = editedNumber * 10 + 0
                "$editedNumber"
            }
            Calculator.SUM -> {
                resultNumber += editedNumber
                editedNumber = 0
                "$resultNumber"
            }
            Calculator.CLEAR -> {
                resultNumber = 0
                editedNumber = 0
                "$resultNumber"
            }
        }
    }
}
