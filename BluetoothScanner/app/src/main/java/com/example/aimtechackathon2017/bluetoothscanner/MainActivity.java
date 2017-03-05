package com.example.aimtechackathon2017.bluetoothscanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity
{
    private static int THRESHOLD = 40;
    private static int COUNT_MAX = 2;
    private static int COUNT_MIN = 0;

    private BluetoothManager bManager;
    private BluetoothAdapter bAdapter;
    private Map<String, Integer> positions;

    private static final int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            if(device == null|| device.getName() == null) return;

            if(device != null && device.getName().trim().equals("DTAG1")) {

                int intensity = -1;
                String mac = device.getAddress().trim().replace(" ",":");
/*
                if (positions.containsKey(mac))
                {
                    intensity = scanRecord[scanRecord.length - 20];

                    if (intensity > THRESHOLD)
                        Log.d("TMP+", "intenzita: " + intensity);
                    else
                        Log.d("TMP-", "intenzita: " + intensity);

                }
*/
                if(positions.containsKey(mac)) {
                    intensity = scanRecord[scanRecord.length - 20];
                    boolean value = isPositionEmpty(mac, intensity);

                    new AsyncTask<String, Object, Object>() {

                        @Override
                        protected Object doInBackground(String... params) {
                            try
                            {
                                URL url = new URL("https://7j62gv0mfg.execute-api.eu-west-1.amazonaws.com/hacking/place");
                                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setRequestProperty("Content-Type", "application/json");

                                JSONArray parent  = new JSONArray();
                                JSONObject entity = new JSONObject();
                                entity.put("available", Boolean.parseBoolean(params[0]));
                                entity.put("place_mac", params[1]);
                                parent.put(entity);

                                OutputStream out = conn.getOutputStream();
                                out.write(parent.toString().getBytes());

                                out.flush();
                                out.close();
                                InputStream is = conn.getInputStream();
                                byte [] array = new byte[1024];

                                is.read(array);
                                String a = new String(array);
                                is.close();

                            }
                            catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            return null;
                        }
                    }.execute(Boolean.toString(value), mac);

                    //task.execute(Boolean.toString(value), mac);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config();
        initializePosition();



        //bAdapter.getBluetoothLeScanner().startScan(new HackathonScanner());
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

    private void initializePosition()
    {
        positions = new HashMap<String, Integer>();
        positions.put("A0:E6:F8:EB:D9:AF",0);
        positions.put("A0:E6:F8:EB:D8:CB",0);
        positions.put("A0:E6:F8:EB:DC:35",0);
    }

    private boolean isPositionEmpty(String mac, int intesity)
    {
        int i = (int)positions.get(mac);

        if (THRESHOLD > Math.abs(intesity)){

            Log.d("PARKING", mac + " " + intesity);

            if(i < COUNT_MAX)
            {
                positions.put(mac, i + 1);
            }
        }
        else {
            Log.d("FREE", mac + " " + intesity);
            if(i > COUNT_MIN)
            {
                positions.put(mac, i - 1);
            }
        }

        return i == COUNT_MIN;
    }
}
