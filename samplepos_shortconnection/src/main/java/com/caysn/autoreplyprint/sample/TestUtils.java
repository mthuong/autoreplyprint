package com.caysn.autoreplyprint.sample;

import android.app.Activity;
import android.widget.Toast;

class TestUtils {

    public static void showMessageOnUiThread(final Activity activity, final String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
