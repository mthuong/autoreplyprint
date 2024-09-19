package com.caysn.autoreplyprint.sample;

import com.caysn.autoreplyprint.AutoReplyPrint;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends Activity implements OnClickListener {

    private MainActivity activity;
    private LinearLayout layoutMain;
    private GRadioGroup rgPort;
    private RadioButton rbBT2, rbBT4, rbNET, rbUSB, rbCOM;
    private ComboBox cbxListBT2, cbxListBT4, cbxListNET, cbxListUSB, cbxListCOMPort, cbxListCOMBaud;
    private Button btnEnumPort;
    private ListView listViewTestFunction;

    private static final int nBaudTable[] = {1200, 2400, 4800, 9600, 19200, 38400, 57600, 115200, 230400, 256000, 500000, 750000, 1125000, 1500000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.app_name) + " " + AutoReplyPrint.INSTANCE.CP_Library_Version());

        activity = this;

        layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
        rbBT2 = (RadioButton) findViewById(R.id.rbBT2);
        rbBT4 = (RadioButton) findViewById(R.id.rbBT4);
        rbNET = (RadioButton) findViewById(R.id.rbNET);
        rbUSB = (RadioButton) findViewById(R.id.rbUSB);
        rbCOM = (RadioButton) findViewById(R.id.rbCOM);
        cbxListBT2 = (ComboBox) findViewById(R.id.cbxLisbBT2);
        cbxListBT4 = (ComboBox) findViewById(R.id.cbxLisbBT4);
        cbxListNET = (ComboBox) findViewById(R.id.cbxListNET);
        cbxListUSB = (ComboBox) findViewById(R.id.cbxLisbUSB);
        cbxListCOMPort = (ComboBox) findViewById(R.id.cbxListCOMPort);
        cbxListCOMBaud = (ComboBox) findViewById(R.id.cbxListCOMBaud);
        btnEnumPort = (Button) findViewById(R.id.btnEnumPort);
        listViewTestFunction = (ListView) findViewById(R.id.listViewTestFunction);

        for (int baud : nBaudTable) {
            cbxListCOMBaud.addString("" + baud);
        }
        cbxListCOMBaud.setText("115200");

        btnEnumPort.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TestFunction.testFunctionOrderedList);
        listViewTestFunction.setAdapter(adapter);
        listViewTestFunction.setOnItemClickListener(onTestFunctionClicked);

        rgPort = new GRadioGroup(rbBT2, rbBT4, rbNET, rbUSB, rbCOM);
        rbBT2.performClick();
        enableBluetooth();
        btnEnumPort.performClick();
        EnableUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnEnumPort:
                EnumPort();
                break;

        }
    }

    private AdapterView.OnItemClickListener onTestFunctionClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String functionName = ((TextView) view).getText().toString();
            if ((functionName == null) || (functionName.isEmpty()))
                return;
            final TestFunction.PortParam port = new TestFunction.PortParam();
            port.bBT2 = rbBT2.isChecked();
            port.bBT4 = rbBT4.isChecked();
            port.bNET = rbNET.isChecked();
            port.bUSB = rbUSB.isChecked();
            port.bCOM = rbCOM.isChecked();
            port.strBT2Address = cbxListBT2.getText();
            port.strBT4Address = cbxListBT4.getText();
            port.strNETAddress = cbxListNET.getText();
            port.strUSBPort = cbxListUSB.getText();
            port.strCOMPort = cbxListCOMPort.getText();
            port.nCOMBaudrate = Integer.parseInt(cbxListCOMBaud.getText());
            DisableUI();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TestFunction fun = new TestFunction();
                        Method m = TestFunction.class.getDeclaredMethod(functionName, Activity.class, TestFunction.PortParam.class);
                        m.invoke(fun, activity, port);
                    } catch (Throwable tr) {
                        tr.printStackTrace();
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EnableUI();
                        }
                    });
                }
            }).start();
        }
    };

    private void EnumCom() {
        cbxListCOMPort.setText("");
        cbxListCOMPort.clear();
        String[] devicePaths = AutoReplyPrint.CP_Port_EnumCom_Helper.EnumCom();
        if (devicePaths != null) {
            for (int i = 0; i < devicePaths.length; ++i) {
                String name = devicePaths[i];
                cbxListCOMPort.addString(name);
                String text = cbxListCOMPort.getText();
                if (text.trim().equals("")) {
                    text = name;
                    cbxListCOMPort.setText(text);
                }
            }
        }
    }

    private void EnumUsb() {
        cbxListUSB.setText("");
        cbxListUSB.clear();
        String[] devicePaths = AutoReplyPrint.CP_Port_EnumUsb_Helper.EnumUsb();
        if (devicePaths != null) {
            for (int i = 0; i < devicePaths.length; ++i) {
                String name = devicePaths[i];
                cbxListUSB.addString(name);
                String text = cbxListUSB.getText();
                if (text.trim().equals("")) {
                    text = name;
                    cbxListUSB.setText(text);
                }
            }
        }
    }

    boolean inNetEnum = false;

    private void EnumNet() {
        if (inNetEnum)
            return;
        inNetEnum = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                IntByReference cancel = new IntByReference(0);
                AutoReplyPrint.CP_OnNetPrinterDiscovered_Callback callback = new AutoReplyPrint.CP_OnNetPrinterDiscovered_Callback() {
                    @Override
                    public void CP_OnNetPrinterDiscovered(String local_ip, String disconvered_mac, final String disconvered_ip, String discovered_name, Pointer private_data) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!cbxListNET.getData().contains(disconvered_ip))
                                    cbxListNET.addString(disconvered_ip);
                                if (cbxListNET.getText().trim().equals("")) {
                                    cbxListNET.setText(disconvered_ip);
                                }
                            }
                        });
                    }
                };
                AutoReplyPrint.INSTANCE.CP_Port_EnumNetPrinter(3000, cancel, callback, null);
                inNetEnum = false;
            }
        }).start();
    }

    boolean inBtEnum = false;

    private void EnumBt() {
        if (!checkBluetoothPermission())
            return;
        if (inBtEnum)
            return;
        inBtEnum = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                IntByReference cancel = new IntByReference(0);
                AutoReplyPrint.CP_OnBluetoothDeviceDiscovered_Callback callback = new AutoReplyPrint.CP_OnBluetoothDeviceDiscovered_Callback() {
                    @Override
                    public void CP_OnBluetoothDeviceDiscovered(String device_name, final String device_address, Pointer private_data) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!cbxListBT2.getData().contains(device_address))
                                    cbxListBT2.addString(device_address);
                                if (cbxListBT2.getText().trim().equals("")) {
                                    cbxListBT2.setText(device_address);
                                }
                            }
                        });
                    }
                };
                AutoReplyPrint.INSTANCE.CP_Port_EnumBtDevice(12000, cancel, callback, null);
                inBtEnum = false;
            }
        }).start();
    }

    boolean inBleEnum = false;

    private void EnumBle() {
        if (!checkBluetoothPermission())
            return;
        if (inBleEnum)
            return;
        inBleEnum = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                IntByReference cancel = new IntByReference(0);
                AutoReplyPrint.CP_OnBluetoothDeviceDiscovered_Callback callback = new AutoReplyPrint.CP_OnBluetoothDeviceDiscovered_Callback() {
                    @Override
                    public void CP_OnBluetoothDeviceDiscovered(String device_name, final String device_address, Pointer private_data) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!cbxListBT4.getData().contains(device_address))
                                    cbxListBT4.addString(device_address);
                                if (cbxListBT4.getText().trim().equals("")) {
                                    cbxListBT4.setText(device_address);
                                }
                            }
                        });
                    }
                };
                AutoReplyPrint.INSTANCE.CP_Port_EnumBleDevice(20000, cancel, callback, null);
                inBleEnum = false;
            }
        }).start();
    }

    private void enableBluetooth() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (null != adapter) {
            if (!adapter.isEnabled()) {
                if (!adapter.enable()) {
                    //finish();
                    Toast.makeText(this, "Failed to enable bluetooth adapter", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean checkGPSEnabled() {
        boolean isEnabled = false;
        LocationManager lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {
            isEnabled = true;
        } else {
            Toast.makeText(this, "Please enable gps else will not search ble printer", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 2);
        }
        return isEnabled;
    }
    public boolean checkLocationPermission() {
        boolean hasPermission = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] LOCATIONGPS = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE
                };
                ActivityCompat.requestPermissions(this, LOCATIONGPS, 1);
            } else {
                hasPermission = true;
            }
        } else {
            hasPermission = true;
        }
        return hasPermission;
    }
    private boolean checkBluetoothPermission() {
        return checkGPSEnabled() && checkLocationPermission();
    }

    private void EnumPort() {
        EnumCom();
        EnumUsb();
        EnumNet();
        EnumBt();
        EnumBle();
    }

    private void DisableUI() {
        rbBT2.setEnabled(false);
        rbBT4.setEnabled(false);
        rbNET.setEnabled(false);
        rbUSB.setEnabled(false);
        rbCOM.setEnabled(false);
        cbxListBT2.setEnabled(false);
        cbxListBT4.setEnabled(false);
        cbxListNET.setEnabled(false);
        cbxListUSB.setEnabled(false);
        cbxListCOMPort.setEnabled(false);
        cbxListCOMBaud.setEnabled(false);
        btnEnumPort.setEnabled(false);
        listViewTestFunction.setEnabled(false);
    }

    private void EnableUI() {
        rbBT2.setEnabled(true);
        rbBT4.setEnabled(true);
        rbNET.setEnabled(true);
        rbUSB.setEnabled(true);
        rbCOM.setEnabled(true);
        cbxListBT2.setEnabled(true);
        cbxListBT4.setEnabled(true);
        cbxListNET.setEnabled(true);
        cbxListUSB.setEnabled(true);
        cbxListCOMPort.setEnabled(true);
        cbxListCOMBaud.setEnabled(true);
        btnEnumPort.setEnabled(true);
        listViewTestFunction.setEnabled(true);
    }
}

