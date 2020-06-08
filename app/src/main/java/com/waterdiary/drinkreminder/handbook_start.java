package com.waterdiary.drinkreminder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import com.waterdiary.drinkreminder.base.MasterBaseAppCompatActivity;

public class handbook_start extends MasterBaseAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_handbook);
        AppCompatTextView login = findViewById(R.id.login);
        AppCompatTextView createAccount = findViewById(R.id.createAccount);
        Button skip = findViewById(R.id.skip);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), handbook_login.class);
                startActivity(intent);
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), handbook_createAccount.class);
                startActivity(intent2);
            }
        });



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), Screen_Dashboard.class);
                String deviceID;
                TelephonyManager telephonyManager;
                telephonyManager = (TelephonyManager) getSystemService(Context.
                        TELEPHONY_SERVICE);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    deviceID = telephonyManager.getDeviceId();
                    intent3.putExtra("USER_ID", deviceID);
                    startActivity(intent3);
                }

                else{
                    Log.d("Error", "No permission!");
                }
            }
        });

    }
}
