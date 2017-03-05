package com.example.aimtechackathon2017.bluetoothscanner;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.util.Log;

/**
 * Created by dzejkob23 on 5.3.17.
 */

class HackathonScanner extends ScanCallback {
    private static int THRESHOLD = 35;

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        ScanRecord record = result.getScanRecord();

        if (result.getDevice() != null && "DTAG1 ".equals(result.getDevice().getName())) {
            byte[] recordData = record.getBytes();
            int intensity = -1;
            String mac = result.getDevice().getAddress().trim().replace(" ",":");

            if (mac.equals("A0:E6:F8:EB:D9:AF"))
            {
                intensity = recordData[recordData.length - 20];

                if (intensity > THRESHOLD)
                    Log.d("TMP+", "intenzita: " + intensity);
                else
                    Log.d("TMP-", "intenzita: " + intensity);
            }

//            if(positions.containsKey(mac)) {
//                boolean value = isPositionEmpty(device.getAddress(), intensity);
//                task.execute(Boolean.toString(value), mac);
//            }
        }
    }
}
