package com.caysn.autoreplyprint.sample;

import com.caysn.autoreplyprint.AutoReplyPrint;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    MainActivity activity;

    CheckBox chkCutter;
    CheckBox chkDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.app_name) + " " + AutoReplyPrint.INSTANCE.CP_Library_Version());

        activity = this;

        chkCutter = findViewById(R.id.chkCutter);
        chkDrawer = findViewById(R.id.chkDrawer);

        findViewById(R.id.btnPrint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);
                final boolean cutPaper = chkCutter.isChecked();
                final boolean kickDrawer = chkDrawer.isChecked();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Test_Pos_SampleTicket_58MM_1(cutPaper, kickDrawer);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                v.setEnabled(true);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showMessageOnUiThread(final String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private Pointer OpenPort()
    {
        Pointer h = Pointer.NULL;
        String[] listUsbPort = AutoReplyPrint.CP_Port_EnumUsb_Helper.EnumUsb();
        if (listUsbPort != null) {
            for (String usbPort : listUsbPort) {
                if (usbPort.contains("0x4B43") || usbPort.contains("0x0FE6")) {
                    h = AutoReplyPrint.INSTANCE.CP_Port_OpenUsb(usbPort, 1);
                    break;
                }
            }
        }
        Log.i(TAG, h == Pointer.NULL ? "OpenPort Failed" : "OpenPort Success");
        if (h == Pointer.NULL) {
            showMessageOnUiThread("OpenPort Failed");
        }
        return h;
    }

    private boolean QueryPrintResult(Pointer h)
    {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_QueryPrintResult(h, 0, 30000);
        Log.i(TAG, result ? "Print Success" : "Print Failed");
        showMessageOnUiThread(result ? "Print Success" : "Print Failed");
        if (!result) {
            LongByReference printer_error_status = new LongByReference();
            LongByReference printer__info_status = new LongByReference();
            LongByReference timestamp_ms_printer_status = new LongByReference();
            if (AutoReplyPrint.INSTANCE.CP_Printer_GetPrinterStatusInfo(h, printer_error_status, printer__info_status, timestamp_ms_printer_status)) {
                AutoReplyPrint.CP_PrinterStatus status = new AutoReplyPrint.CP_PrinterStatus(printer_error_status.getValue(), printer__info_status.getValue());
                String error_status_string = String.format("Printer Error Status: 0x%04X", printer_error_status.getValue() & 0xffff);
                if (status.ERROR_OCCURED()) {
                    if (status.ERROR_CUTTER())
                        error_status_string += "[ERROR_CUTTER]";
                    if (status.ERROR_FLASH())
                        error_status_string += "[ERROR_FLASH]";
                    if (status.ERROR_NOPAPER())
                        error_status_string += "[ERROR_NOPAPER]";
                    if (status.ERROR_VOLTAGE())
                        error_status_string += "[ERROR_VOLTAGE]";
                    if (status.ERROR_MARKER())
                        error_status_string += "[ERROR_MARKER]";
                    if (status.ERROR_ENGINE())
                        error_status_string += "[ERROR_ENGINE]";
                    if (status.ERROR_OVERHEAT())
                        error_status_string += "[ERROR_OVERHEAT]";
                    if (status.ERROR_COVERUP())
                        error_status_string += "[ERROR_COVERUP]";
                    if (status.ERROR_MOTOR())
                        error_status_string += "[ERROR_MOTOR]";
                }
                Log.i(TAG, error_status_string);
                showMessageOnUiThread(error_status_string);
            } else {
                Log.i(TAG, "CP_Printer_GetPrinterStatusInfo Failed");
                showMessageOnUiThread("CP_Printer_GetPrinterStatusInfo Failed");
            }
        }
        return result;
    }

    private void Test_Pos_SampleTicket_58MM_1(boolean cutPaper, boolean kickDrawer)
    {
        Pointer h = OpenPort();
        if (h != Pointer.NULL) {

            Bitmap bitmap = getImageFromAssetsFile("RasterImage/blackwhite.png");
            AutoReplyPrint.CP_Pos_PrintRasterImageFromData_Helper.PrintRasterImageFromBitmap(h, bitmap.getWidth(), bitmap.getHeight(), bitmap, AutoReplyPrint.CP_ImageBinarizationMethod_Thresholding, AutoReplyPrint.CP_ImageCompressionMethod_None);

            if (kickDrawer) {
                AutoReplyPrint.INSTANCE.CP_Pos_KickOutDrawer(h, 0, 100, 100);
                AutoReplyPrint.INSTANCE.CP_Pos_KickOutDrawer(h, 1, 100, 100);
            }

            if (cutPaper) {
                AutoReplyPrint.INSTANCE.CP_Pos_FeedAndHalfCutPaper(h);
            } else {
                AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 10);
            }

            QueryPrintResult(h);

            AutoReplyPrint.INSTANCE.CP_Port_Close(h);
        }
    }
}
