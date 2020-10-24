package com.itis.group11801.fedotova.homework.store.reducer

import com.itis.group11801.fedotova.homework.store.actions.MainActivityAction
import com.itis.group11801.fedotova.homework.store.actions.MainActivityAction.*
import com.itis.group11801.fedotova.homework.store.state.InputMathData.MathDataType.*
import com.itis.group11801.fedotova.homework.store.state.MainActivityState
import com.itis.group11801.fedotova.homework.store.state.getMathDataType

class MainActivityReducer {

    fun reduce(state: MainActivityState, action: MainActivityAction): MainActivityState {
        return when (action) {
            is MathStarted -> state.copy(isLoading = true)
            is MathSuccess -> state.copy(
                isLoading = false,
                error = null,
                mathData = action.mathData
            )
            is MathError -> state.copy(isLoading = false, error = action.message)
            is MathDataInput.First -> state.copy(
                inputMathData = state.inputMathData?.copy(
                    firstType = FIRST_NUMBER,
                    secondType = state.getMathDataType(FIRST_NUMBER)
                ),
                mathData = state.mathData?.copy(firstNumber = action.number)
            )
            is MathDataInput.Second -> state.copy(
                inputMathData = state.inputMathData?.copy(
                    firstType = SECOND_NUMBER,
                    secondType = state.getMathDataType(SECOND_NUMBER)
                ),
                mathData = state.mathData?.copy(secondNumber = action.number)
            )
            is MathDataInput.Result -> state.copy(
                inputMathData = state.inputMathData?.copy(
                    firstType = RESULT,
                    secondType = state.getMathDataType(RESULT)
                ),
                mathData = state.mathData?.copy(result = action.number)
            )
        }
    }
}
