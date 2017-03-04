package com.example.aimtechackathon2017.bluetoothscanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity
{
    private BluetoothManager bManager;
    private BluetoothAdapter bAdapter;

    private static final int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            Log.d("SRACKY", device.getName());

            if(device != null && device.getName().trim().equals("DTAG1")) {
                String mac = "";
                for (int i = 12; i >= 7; i--)
                    mac += String.format("%02X ", scanRecord[i]);

                Log.d("SRACKY", "MAC: " + mac);

                String policko = "";
                for (int i = scanRecord.length-1; i >= 0; i--)
                    policko += String.format("%02X ", scanRecord[i]);

                Log.d("SRACKY", "policko : " + policko);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config();

        bAdapter.startLeScan(leScanCallback);
        //bAdapter.stopLeScan(leScanCallback);
    }

    private void config()
    {
        bManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bAdapter = bManager.getAdapter();

        if (bAdapter != null && !bAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
        }
    }
}
