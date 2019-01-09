package com.gk.smsverificationtest;

import android.app.Application;

public class SMSVerificationApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new AppSignatureHelper(this).getAppSignatures();
    }
}
