package com.caysn.autoreplyprint.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.caysn.autoreplyprint.AutoReplyPrint;

public class MenuActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle(getResources().getString(R.string.app_name) + " " + AutoReplyPrint.INSTANCE.CP_Library_Version());

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button1:
                startActivity(new Intent(this, MainActivity1.class));
                break;

            case R.id.button2:
                startActivity(new Intent(this, MainActivity2.class));
                break;
        }
    }

}

