package com.itis.group11801.fedotova.homework.store.sideeffects

import com.itis.group11801.fedotova.homework.data.MathApi
import com.itis.group11801.fedotova.homework.store.actions.MainActivityAction
import com.itis.group11801.fedotova.homework.store.state.MainActivityState
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType

class InputMathDataSideEffect(
    private val mathApi: MathApi
) : MainActivitySideEffect {

    override fun invoke(
        actions: Observable<MainActivityAction>,
        state: () -> MainActivityState
    ): Observable<out MainActivityAction> {
        return actions.ofType<MainActivityAction.MathDataInput>()
            .switchMap {
                val thisState= state()
                mathApi.getMathResult(thisState.mathData, thisState.inputMathData)
                    .map<MainActivityAction> { MainActivityAction.MathSuccess(it) }
                    .onErrorReturn { MainActivityAction.MathError(it.message) }
                    .toObservable()
                    .startWith(MainActivityAction.MathStarted)
            }
    }
}
