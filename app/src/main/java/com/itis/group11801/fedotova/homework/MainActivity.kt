package com.itis.group11801.fedotova.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.itis.group11801.fedotova.homework.data.MathApi
import com.itis.group11801.fedotova.homework.store.MainActivityStore
import com.itis.group11801.fedotova.homework.store.actions.MainActivityAction.*
import com.itis.group11801.fedotova.homework.store.sideeffects.InputMathDataSideEffect
import com.itis.group11801.fedotova.homework.store.state.MainActivityState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val store = MainActivityStore(listOf(InputMathDataSideEffect(MathApi())))

    private val disposables = CompositeDisposable()

    private var lock = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()

        disposables.add(
            store.state
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::render)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun setupViews() {
        firstNumberEditText.addTextChangedListener {
            if (!lock && "$it".isNotBlank()) {
                store.addAction(MathDataInput.First("$it".toInt()))
            }
        }
        secondNumberEditText.addTextChangedListener {
            if (!lock && "$it".isNotBlank()) {
                store.addAction(MathDataInput.Second("$it".toInt()))
            }
        }
        resultEditText.addTextChangedListener {
            if (!lock && "$it".isNotBlank()) {
                store.addAction(MathDataInput.Result("$it".toInt()))
            }
        }
    }

    private fun render(state: MainActivityState) {
        lock = true
        progressBar.isVisible = state.isLoading
        state.mathData.let {
            firstNumberEditText.renderText("${it.firstNumber}")
            secondNumberEditText.renderText("${it.secondNumber}")
            resultEditText.renderText("${it.result}")
        }
        state.error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        lock = false
    }

    private fun EditText.renderText(text: String) {
        if (text != "null" && text != "${this.text}") {
            setText(text)
            setSelection(text.length)
        } else return
    }
}
