package com.caysn.autoreplyprint.sample;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class TestUtils {
    public static byte[] ReadFromFile(String fileName) {
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(fileName);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Throwable tr) {
            tr.printStackTrace();
        }

        return data;
    }

    public static Bitmap getImageFromAssetsFile(Context ctx, String fileName) {
        Bitmap image = null;
        AssetManager am = ctx.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void showMessageOnUiThread(final Activity activity, final String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
