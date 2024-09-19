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
import android.text.format.DateFormat;
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
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity implements OnClickListener {

    private MainActivity activity;
    private LinearLayout layoutMain;
    private GRadioGroup rgPort;
    private RadioButton rbBT2, rbBT4, rbNET, rbUSB, rbCOM;
    private ComboBox cbxListBT2, cbxListBT4, cbxListNET, cbxListUSB, cbxListCOMPort, cbxListCOMBaud;
    private Button btnEnumPort, btnOpenPort, btnClosePort;
    private ListView listViewTestFunction;

    private static final int nBaudTable[] = {1200, 2400, 4800, 9600, 19200, 38400, 57600, 115200, 230400, 256000, 500000, 750000, 1125000, 1500000};

    private Pointer h = Pointer.NULL;

    AutoReplyPrint.CP_OnPortOpenedEvent_Callback opened_callback = new AutoReplyPrint.CP_OnPortOpenedEvent_Callback() {
        @Override
        public void CP_OnPortOpenedEvent(Pointer handle, String name, Pointer private_data) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Open Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    AutoReplyPrint.CP_OnPortOpenFailedEvent_Callback openfailed_callback = new AutoReplyPrint.CP_OnPortOpenFailedEvent_Callback() {
        @Override
        public void CP_OnPortOpenFailedEvent(Pointer handle, String name, Pointer private_data) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Open Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    AutoReplyPrint.CP_OnPortClosedEvent_Callback closed_callback = new AutoReplyPrint.CP_OnPortClosedEvent_Callback() {
        @Override
        public void CP_OnPortClosedEvent(Pointer h, Pointer private_data) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ClosePort();
                }
            });
        }
    };
    private void AddCallback() {
        AutoReplyPrint.INSTANCE.CP_Port_AddOnPortOpenedEvent(opened_callback, Pointer.NULL);
        AutoReplyPrint.INSTANCE.CP_Port_AddOnPortOpenFailedEvent(openfailed_callback, Pointer.NULL);
        AutoReplyPrint.INSTANCE.CP_Port_AddOnPortClosedEvent(closed_callback, Pointer.NULL);
    }
    private void RemoveCallback() {
        AutoReplyPrint.INSTANCE.CP_Port_RemoveOnPortOpenedEvent(opened_callback);
        AutoReplyPrint.INSTANCE.CP_Port_RemoveOnPortOpenFailedEvent(openfailed_callback);
        AutoReplyPrint.INSTANCE.CP_Port_RemoveOnPortClosedEvent(closed_callback);
    }

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
        btnOpenPort = (Button) findViewById(R.id.btnOpenPort);
        btnClosePort = (Button) findViewById(R.id.btnClosePort);
        listViewTestFunction = (ListView) findViewById(R.id.listViewTestFunction);

        for (int baud : nBaudTable) {
            cbxListCOMBaud.addString("" + baud);
        }
        cbxListCOMBaud.setText("115200");

        btnEnumPort.setOnClickListener(this);
        btnOpenPort.setOnClickListener(this);
        btnClosePort.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TestFunction.testFunctionOrderedList);
        listViewTestFunction.setAdapter(adapter);
        listViewTestFunction.setOnItemClickListener(onTestFunctionClicked);

        rgPort = new GRadioGroup(rbBT2, rbBT4, rbNET, rbUSB, rbCOM);
        rbBT2.performClick();
        enableBluetooth();
        btnEnumPort.performClick();
        RefreshUI();

        AddCallback();
    }

    @Override
    protected void onDestroy() {
        RemoveCallback();

        btnClosePort.performClick();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnEnumPort:
                EnumPort();
                break;

            case R.id.btnOpenPort:
                OpenPort();
                break;

            case R.id.btnClosePort:
                ClosePort();
                break;

        }
    }

    private AdapterView.OnItemClickListener onTestFunctionClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String functionName = ((TextView) view).getText().toString();
            if ((functionName == null) || (functionName.isEmpty()))
                return;
            DisableUI();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TestFunction fun = new TestFunction();
                        fun.ctx = activity;
                        Method m = TestFunction.class.getDeclaredMethod(functionName, Pointer.class);
                        m.invoke(fun, h);
                    } catch (Throwable tr) {
                        tr.printStackTrace();
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RefreshUI();
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

    private void OpenPort() {
        DisableUI();
        final boolean rbBT2Checked = rbBT2.isChecked();
        final boolean rbBT4Checked = rbBT4.isChecked();
        final boolean rbNETChecked = rbNET.isChecked();
        final boolean rbUSBChecked = rbUSB.isChecked();
        final boolean rbCOMChecked = rbCOM.isChecked();
        final String strBT2Address = cbxListBT2.getText();
        final String strBT4Address = cbxListBT4.getText();
        final String strNETAddress = cbxListNET.getText();
        final String strUSBPort = cbxListUSB.getText();
        final String strCOMPort = cbxListCOMPort.getText();
        final int nComBaudrate = Integer.parseInt(cbxListCOMBaud.getText());
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (rbBT2Checked) {
                    h = AutoReplyPrint.INSTANCE.CP_Port_OpenBtSpp(strBT2Address, 0);
                } else if (rbBT4Checked) {
                    h = AutoReplyPrint.INSTANCE.CP_Port_OpenBtBle(strBT4Address, 0);
                } else if (rbNETChecked) {
                    h = AutoReplyPrint.INSTANCE.CP_Port_OpenTcp(null, strNETAddress, (short) 9100, 5000, 0);
                } else if (rbUSBChecked) {
                    h = AutoReplyPrint.INSTANCE.CP_Port_OpenUsb(strUSBPort, 0);
                } else if (rbCOMChecked) {
                    h = AutoReplyPrint.INSTANCE.CP_Port_OpenCom(strCOMPort, nComBaudrate, AutoReplyPrint.CP_ComDataBits_8, AutoReplyPrint.CP_ComParity_NoParity, AutoReplyPrint.CP_ComStopBits_One, AutoReplyPrint.CP_ComFlowControl_XonXoff, 0);
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RefreshUI();
                    }
                });
            }
        }).start();
    }

    private void ClosePort() {
        if (h != Pointer.NULL) {
            AutoReplyPrint.INSTANCE.CP_Port_Close(h);
            h = Pointer.NULL;
        }
        RefreshUI();
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
        btnOpenPort.setEnabled(false);
        btnClosePort.setEnabled(false);
        listViewTestFunction.setEnabled(false);
    }

    private void RefreshUI() {
        rbBT2.setEnabled(h == Pointer.NULL);
        rbBT4.setEnabled(h == Pointer.NULL);
        rbNET.setEnabled(h == Pointer.NULL);
        rbUSB.setEnabled(h == Pointer.NULL);
        rbCOM.setEnabled(h == Pointer.NULL);
        cbxListBT2.setEnabled(h == Pointer.NULL);
        cbxListBT4.setEnabled(h == Pointer.NULL);
        cbxListNET.setEnabled(h == Pointer.NULL);
        cbxListUSB.setEnabled(h == Pointer.NULL);
        cbxListCOMPort.setEnabled(h == Pointer.NULL);
        cbxListCOMBaud.setEnabled(h == Pointer.NULL);
        btnEnumPort.setEnabled(h == Pointer.NULL);
        btnOpenPort.setEnabled(h == Pointer.NULL);
        btnClosePort.setEnabled(h != Pointer.NULL);
        listViewTestFunction.setEnabled(h != Pointer.NULL);

        int visibility = (h == Pointer.NULL) ? View.VISIBLE : View.GONE;
        if (!rbBT2.isChecked()) {
            rbBT2.setVisibility(visibility);
            cbxListBT2.setVisibility(visibility);
        }
        if (!rbBT4.isChecked()) {
            rbBT4.setVisibility(visibility);
            cbxListBT4.setVisibility(visibility);
        }
        if (!rbNET.isChecked()) {
            rbNET.setVisibility(visibility);
            cbxListNET.setVisibility(visibility);
        }
        if (!rbUSB.isChecked()) {
            rbUSB.setVisibility(visibility);
            cbxListUSB.setVisibility(visibility);
        }
        if (!rbCOM.isChecked()) {
            rbCOM.setVisibility(visibility);
            cbxListCOMPort.setVisibility(visibility);
            cbxListCOMBaud.setVisibility(visibility);
        }
    }
}

