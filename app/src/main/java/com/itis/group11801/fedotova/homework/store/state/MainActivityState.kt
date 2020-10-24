package com.itis.group11801.fedotova.homework.store.state

data class MainActivityState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val mathData: MathData = MathData(),
    val inputMathData: InputMathData = InputMathData()
)

data class MathData(
    val firstNumber: Int? = null,
    val secondNumber: Int? = null,
    val result: Int? = null
)

data class InputMathData(
    val firstType: MathDataType? = null,
    val secondType: MathDataType? = null
) {
    enum class MathDataType {
        FIRST_NUMBER, SECOND_NUMBER, RESULT
    }
}

fun MainActivityState.getMathDataType(
    mathDataType: InputMathData.MathDataType
): InputMathData.MathDataType? {
    return if (inputMathData?.firstType == mathDataType) {
        inputMathData.secondType
    } else {
        inputMathData?.firstType
    }
}
