package com.itis.group11801.fedotova.google_pay_app

import android.app.Activity
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object PaymentUtils {

    private const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST

    private const val COUNTRY_CODE = "RU"

    private const val CURRENCY_CODE = "RUB"

    private const val PAYMENT_GATEWAY_TOKENIZATION_NAME = "example"

    private val paymentGatewayTokenizationParameters = mapOf(
        "gateway" to PAYMENT_GATEWAY_TOKENIZATION_NAME,
        "gatewayMerchantId" to "exampleGatewayMerchantId"
    )

    private val baseRequest = JSONObject()
        .put("apiVersion", 2)
        .put("apiVersionMinor", 0)


    private val merchantInfo: JSONObject = JSONObject()
        .put("merchantName", "Example Merchant")

    private val gatewayTokenizationSpecification = JSONObject()
        .put("type", "PAYMENT_GATEWAY")
        .put("parameters", JSONObject(paymentGatewayTokenizationParameters))

    private val allowedCardNetworks = JSONArray(
        listOf(
            "MASTERCARD",
            "VISA"
        )
    )

    private val allowedCardAuthMethods = JSONArray(
        listOf(
            "PAN_ONLY",
            "CRYPTOGRAM_3DS"
        )
    )

    private val baseCardPaymentMethod = JSONObject()
        .put("type", "CARD")
        .put(
            "parameters", JSONObject(
                mapOf(
                    "allowedAuthMethods" to allowedCardAuthMethods,
                    "allowedCardNetworks" to allowedCardNetworks
                )
            )
        )

    private val cardPaymentMethod: JSONObject =
        baseCardPaymentMethod.put(
            "tokenizationSpecification",
            gatewayTokenizationSpecification
        )

    fun getRequest(): JSONObject? {
        return try {
            JSONObject(baseRequest.toString()).put(
                // TODO: base or nor
                "allowedPaymentMethods", JSONArray().put(cardPaymentMethod)
            )
        } catch (e: JSONException) {
            null
        }
    }

    fun createPaymentsClient(activity: Activity): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(PAYMENTS_ENVIRONMENT)
            .build()
        return Wallet.getPaymentsClient(activity, walletOptions)
    }

    private fun getTransactionInfo(price: String) =
        JSONObject()
            .put("totalPrice", price)
            .put("totalPriceStatus", "FINAL")
            .put("countryCode", COUNTRY_CODE)
            .put("currencyCode", CURRENCY_CODE)

    fun getPaymentDataRequest(price: String): JSONObject? {
        return try {
            JSONObject(baseRequest.toString())
                .put(
                    "allowedPaymentMethods", JSONArray()
                        .put(cardPaymentMethod)
                )
                .put("allowedPaymentMethods", JSONArray().put(cardPaymentMethod))
                .put("transactionInfo", getTransactionInfo(price))
                .put("merchantInfo", merchantInfo)

        } catch (e: JSONException) {
            null
        }
    }
}
