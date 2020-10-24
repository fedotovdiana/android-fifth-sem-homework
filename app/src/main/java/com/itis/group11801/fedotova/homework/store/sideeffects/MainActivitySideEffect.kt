package com.itis.group11801.fedotova.homework.store.sideeffects

import com.freeletics.rxredux.SideEffect
import com.itis.group11801.fedotova.homework.store.actions.MainActivityAction
import com.itis.group11801.fedotova.homework.store.state.MainActivityState

typealias MainActivitySideEffect = SideEffect<MainActivityState, MainActivityAction>

