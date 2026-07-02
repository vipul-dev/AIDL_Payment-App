package com.dev.paymentapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.dev.paymentapp.IPaymentCallback
import com.dev.paymentapp.IPaymentService
import com.dev.paymentapp.TransactionResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds

class PaymentService : Service() {

    override fun onCreate() {
        super.onCreate()

        Log.e("PAYMENT_APP", "Service Created")
    }
    val binder = object : IPaymentService.Stub() {

        override fun startPayment(
            amount: Double,
            callback: IPaymentCallback?
        ) {

            CoroutineScope(Dispatchers.IO).launch {

                try {
                  delay(5000.milliseconds)

                    val result = TransactionResult(
                        transactionId = UUID.randomUUID().toString(),
                        status = "Success",
                        amount = amount
                    )

                    callback?.onSuccess(result)

                } catch (e: Exception) {
                    callback?.onFailure(
                        e.message ?: "Unknown error"
                    )
                }
            }


        }

        override fun cancelPayment(): Boolean {
            return true
        }


    }


    override fun onBind(p0: Intent?): IBinder? {
        Log.e("PAYMENT_APP", "onBind")
        return binder
    }
}
