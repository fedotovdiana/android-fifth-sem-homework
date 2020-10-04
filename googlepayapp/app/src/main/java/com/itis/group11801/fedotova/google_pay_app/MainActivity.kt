package com.itis.group11801.fedotova.google_pay_app

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import com.itis.group11801.fedotova.google_pay_app.PaymentUtils.createPaymentsClient
import com.itis.group11801.fedotova.google_pay_app.PaymentUtils.getPaymentDataRequest
import com.itis.group11801.fedotova.google_pay_app.PaymentUtils.getRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOAD_PAYMENT_DATA_REQUEST_CODE = 333
    }

    private lateinit var paymentsClient: PaymentsClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paymentsClient = createPaymentsClient(this)
        possiblyShowGooglePayButton()
        googlePayButton.setOnClickListener { requestPayment() }
    }

    private fun possiblyShowGooglePayButton() {
        val isReadyToPayJson = getRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                Log.w("isReadyToPay failed", exception)
            }
        }
    }

    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            googlePayButton.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "Google Pay is not available on this device", Toast.LENGTH_LONG).show();
        }
    }

    private fun requestPayment() {

        googlePayButton.isClickable = false

        val paymentDataRequestJson = getPaymentDataRequest("1")
        if (paymentDataRequestJson == null) {
            Log.e("RequestPayment", "Can't fetch payment data request")
            return
        }
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        if (request != null) {
            AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                this,
                LOAD_PAYMENT_DATA_REQUEST_CODE
            )
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK ->
                        data?.let { intent ->
                            PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                        }
                    Activity.RESULT_CANCELED -> { }
                    AutoResolveHelper.RESULT_ERROR -> {
                        AutoResolveHelper.getStatusFromIntent(data)?.let {
                            Toast.makeText(this,"Error code: ${it.statusCode}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                googlePayButton.isClickable = true
            }
        }
    }

    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson() ?: return

        val paymentMethodData =
            JSONObject(paymentInformation).getJSONObject("paymentMethodData")

        val token = paymentMethodData
            .getJSONObject("tokenizationData")
            .getString("token")

        Toast.makeText(this, token, Toast.LENGTH_LONG).show()
    }
}