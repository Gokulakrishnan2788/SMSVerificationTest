package com.gk.smsverificationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

import static java.nio.file.Paths.get;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    OTPReceiveListener otpReceiver = null;
    public void initOTPListener(OTPReceiveListener otpReceiver){
        this.otpReceiver = otpReceiver;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            String otp = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE).toString();

            switch(status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    // Get SMS message contents
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    // Extract one-time code from the message and complete verification
                    // by sending the code back to your server.

                    // Extract one-time code from the message and complete verification
                    // by sending the code back to your server for SMS authenticity.
                    // But here we are just passing it to MainActivity
                    if (otpReceiver != null) {
                       // otp = otp.replace("<#> Your ExampleApp code is: ", "").split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                        otpReceiver.onOTPReceived(otp);
                    }
                    break;
                case CommonStatusCodes.TIMEOUT:
                    // Waiting for SMS timed out (5 minutes)
                    // Handle the error ...
                    otpReceiver.onOTPTimeOut();
                    break;
            }
        }


    }

    interface OTPReceiveListener {

         public void onOTPReceived(String otp);
         public void onOTPTimeOut();


    }
}
