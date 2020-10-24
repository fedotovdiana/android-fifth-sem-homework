package com.itis.group11801.fedotova.homework.data

import com.itis.group11801.fedotova.homework.store.state.InputMathData
import com.itis.group11801.fedotova.homework.store.state.InputMathData.MathDataType.*
import com.itis.group11801.fedotova.homework.store.state.MathData
import io.reactivex.Single
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MathApi {

    companion object {
        private const val DELAY_TIME = 5L
    }

    fun getMathResult(mathData: MathData, inputMathData: InputMathData): Single<MathData> {
        with(mathData) {
            if (inputMathData.secondType == null) return Single.just(mathData)
            return when (inputMathData.firstType) {
                FIRST_NUMBER -> {
                    if (inputMathData.secondType == SECOND_NUMBER) {
                        Single.just(
                            MathData(
                                firstNumber,
                                secondNumber,
                                secondNumber?.let { firstNumber?.plus(secondNumber) }
                            )
                        )
                    } else {
                        Single.just(
                            MathData(
                                firstNumber,
                                firstNumber?.let { result?.minus(firstNumber) },
                                result,
                            )
                        )
                    }
                }
                SECOND_NUMBER -> {
                    if (inputMathData.secondType == FIRST_NUMBER) {
                        Single.just(
                            MathData(
                                firstNumber,
                                secondNumber,
                                firstNumber?.let { secondNumber?.plus(firstNumber) },
                            )
                        )
                    } else {
                        Single.just(
                            MathData(
                                secondNumber?.let { result?.minus(secondNumber) },
                                secondNumber,
                                result,
                            )
                        )
                    }
                }
                RESULT -> {
                    if (inputMathData.secondType == FIRST_NUMBER) {
                        Single.just(
                            MathData(
                                firstNumber,
                                firstNumber?.let { result?.minus(firstNumber) },
                                result
                            )
                        )
                    } else {
                        Single.just(
                            MathData(
                                secondNumber?.let { result?.minus(secondNumber) },
                                secondNumber,
                                result
                            )
                        )
                    }
                }
                else -> return Single.error(Exception("Math exception"))
            }.delay(DELAY_TIME, TimeUnit.SECONDS)
        }
    }
}

