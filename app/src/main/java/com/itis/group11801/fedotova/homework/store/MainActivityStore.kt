package com.itis.group11801.fedotova.homework.store

import com.freeletics.rxredux.reduxStore
import com.itis.group11801.fedotova.homework.store.actions.MainActivityAction
import com.itis.group11801.fedotova.homework.store.reducer.MainActivityReducer
import com.itis.group11801.fedotova.homework.store.sideeffects.MainActivitySideEffect
import com.itis.group11801.fedotova.homework.store.state.MainActivityState
import io.reactivex.subjects.PublishSubject

class MainActivityStore(
    sideEffects: List<MainActivitySideEffect>
) {

    private val actionRelay = PublishSubject.create<MainActivityAction>()

    val state = actionRelay.reduxStore(
        MainActivityState(),
        sideEffects,
        MainActivityReducer()::reduce
    )

    fun addAction(action: MainActivityAction) {
        actionRelay.onNext(action)
    }
}