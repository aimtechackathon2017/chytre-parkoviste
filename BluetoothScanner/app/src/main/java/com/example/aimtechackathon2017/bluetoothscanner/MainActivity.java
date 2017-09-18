package com.example.aimtechackathon2017.bluetoothscanner;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity
{
    private static final int  DEFAULT_THRESHOLD = 60;//40;
    private int threshold = 60;
    private static int COUNT_MAX = 2;
    private static int COUNT_MIN = 0;

    private BluetoothManager bManager;
    private BluetoothAdapter bAdapter;
    private Map<String, Integer> positions;

    private static final int REQUEST_ENABLE_BT = 1;

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            if(device == null|| device.getName() == null) return;

            if(device != null && device.getName().trim().equals("DTAG1")) {

                int intensity = -1;
                String mac = device.getAddress().trim().replace(" ",":");

                if(positions.containsKey(mac)) {
                    byte[] pairedMac = Arrays.copyOfRange(scanRecord, 35, 41);

                    //if place is paired with place
                    if(positions.containsKey(bytesToHexMac(pairedMac)))
                        return;

                    intensity = scanRecord[scanRecord.length - 20];
                    boolean value = isPositionEmpty(mac, intensity);

                    new AsyncTask<String, Object, Object>() {

                        @Override
                        protected Object doInBackground(String... params) {
                            try
                            {
                                URL url = new URL("https://i07qj1v6hk.execute-api.eu-central-1.amazonaws.com/prod/place");
                                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setRequestProperty("Content-Type", "application/json");
                                conn.setDoOutput(true);
                                conn.setDoInput(true);

                                JSONArray parent  = new JSONArray();
                                JSONObject entity = new JSONObject();
                                entity.put("available", Boolean.parseBoolean(params[0]));
                                entity.put("place_mac", params[1]);

                                parent.put(entity);

                                if(!Boolean.parseBoolean(params[0])) {
                                    for (String place_mac : positions.keySet()) {
                                        if (!place_mac.equalsIgnoreCase(params[1])) {
                                            JSONObject anotherEntity = new JSONObject();
                                            anotherEntity.put("available", true);
                                            anotherEntity.put("place_mac", place_mac);

                                            parent.put(anotherEntity);
                                        }
                                    }
                                }

                                OutputStream out = conn.getOutputStream();
                                out.write(parent.toString().getBytes());

                                out.flush();
                                out.close();

                                InputStream is = conn.getInputStream();//getErrorStream();
                                byte [] array = new byte[1024];

                                is.read(array);
                                //String result = new String(array);
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
    }

    public void startScan(View v)
    {
        TextView myAwesomeTextView = (TextView)findViewById(R.id.activeTV);
        myAwesomeTextView.setText(R.string.active);

        EditText thresholdEditText = (EditText)findViewById(R.id.thresholdEditText);
        String threshold = thresholdEditText.getText().toString();

        if (TextUtils.isEmpty(threshold)) {
            thresholdEditText.setText(Integer.toString(DEFAULT_THRESHOLD));
            this.threshold = DEFAULT_THRESHOLD;
        }
        else {
            this.threshold = Integer.parseInt(threshold);
        }

        myAwesomeTextView.setText(R.string.active);

        if(bAdapter != null)
            bAdapter.startLeScan(leScanCallback);
    }

    public void stopScan(View v)
    {
        TextView myAwesomeTextView = (TextView)findViewById(R.id.activeTV);
        myAwesomeTextView.setText(R.string.inactive);

        if(bAdapter != null)
            bAdapter.stopLeScan(leScanCallback);
    }

    @Override
    public void onDestroy() {
        stopScan(null);
        super.onDestroy();
    }

    private void config()
    {
        bManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bAdapter = bManager.getAdapter();

        if (bAdapter != null && !bAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,REQUEST_ENABLE_BT);
        }

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if(wifiManager != null && !wifiManager.isWifiEnabled()){
            WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            wifi.setWifiEnabled(true);
        }

        LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(lManager != null && !(lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
        }

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
    }

    private void initializePosition()
    {
        positions = new HashMap<String, Integer>();
        positions.put("A0:E6:F8:EB:C9:13",0);
        positions.put("A0:E6:F8:EB:C8:E7",0);
        positions.put("A0:E6:F8:EB:C9:32",0);
    }

    private boolean isPositionEmpty(String mac, int intesity)
    {
        int i = (int)positions.get(mac);

        if (threshold > Math.abs(intesity)){

            Log.d("PARKING", mac + " " + intesity);

            return false;

            /*if(i < COUNT_MAX)
            {
                positions.put(mac, i + 1);
            }*/
        }
        else {
            Log.d("FREE", mac + " " + intesity);
            /*if(i > COUNT_MIN)
            {
                positions.put(mac, i - 1);
            }*/
            return true;
        }

        //return i == COUNT_MIN;
    }

    public String bytesToHexMac(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        StringBuilder sb = new StringBuilder();
        for(int i = hexChars.length - 1; i >= 0; i -= 2){
            sb.append(hexChars[i - 1]);
            sb.append(hexChars[i]);

            if(i - 1 != 0)
                sb.append(':');
        }

        return sb.toString();
    }
}
