package com.itis.group11801.fedotova.fifthsemesterhomeworks

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val KEY_CALC_PREFS = "CALC_PREFS"
        private const val KEY_RESULT_NUMBER = "RESULT_NUMBER"
        private const val KEY_EDITED_NUMBER = "EDITED_NUMBER"
        private const val KEY_RESULT_TEXT = "RESULT_TEXT"
    }

    private lateinit var prefs: SharedPreferences
    private var resultNumber = 0
    private var editedNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences(KEY_CALC_PREFS, Context.MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        setUpChangeTheme()
        setUpCalculator()
    }

    override fun onResume() {
        super.onResume()
        restorePrefs()
    }

    override fun onPause() {
        super.onPause()
        savePrefs()
    }

    private fun savePrefs() {
        prefs.edit().apply {
            putInt(KEY_RESULT_NUMBER, resultNumber)
            putInt(KEY_EDITED_NUMBER, editedNumber)
            putString(KEY_RESULT_TEXT, resultTextView.text.toString())
        }.apply()
    }

    private fun restorePrefs() {
        resultNumber = prefs.getInt(KEY_RESULT_NUMBER, 0)
        editedNumber = prefs.getInt(KEY_EDITED_NUMBER, 0)
        resultTextView.text = prefs.getString(KEY_RESULT_TEXT, resources.getString(R.string.text_result))
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
        clearButton.setOnClickListener { updateResultTextView(Calculator.CLEAR) }
        sumButton.setOnClickListener { updateResultTextView(Calculator.SUM) }
        resultButton.setOnClickListener { updateResultTextView(Calculator.RESULT) }
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
            Calculator.SUM, Calculator.RESULT -> {
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
