package com.saptrishi.hotelkotapp.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.view.activities.Device_Registration;

public class IP_Registration extends AppCompatActivity {
    EditText edtIP, edtPort, edtProjectPath, edtServicePath;
    Button btnSaveConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_registration);

        edtIP = findViewById(R.id.edtIP);
        edtPort = findViewById(R.id.edtPort);
        edtProjectPath = findViewById(R.id.edtProjectPath);
        edtServicePath = findViewById(R.id.edtServicePath);
        btnSaveConfig = findViewById(R.id.btnSaveConfig);
        SharedPreferences prefs = getSharedPreferences("Config", MODE_PRIVATE);

// Load and validate each field
        String ip = prefs.getString("ip", "");
        if (ip.isEmpty()) ip = "192.168.0.142";
        edtIP.setText(ip);

        String port = prefs.getString("port", "");
        if (port.isEmpty()) port = "Hotel_ServiceApp";
        edtPort.setText(port);

        String projectPath = prefs.getString("projectPath", "");
        if (projectPath.isEmpty()) projectPath = "Kanpur_HotelKotApp_Service.svc";
        edtProjectPath.setText(projectPath);

        String servicePath = prefs.getString("servicePath", "");
        if (servicePath.isEmpty()) servicePath = "Hotel_DeviceReg_Post";
        edtServicePath.setText(servicePath);


        http://192.168.0.142/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/Hotel_DeviceReg_Post/
        btnSaveConfig.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("ip", edtIP.getText().toString().trim());
            editor.putString("port", edtPort.getText().toString().trim());
            editor.putString("projectPath", edtProjectPath.getText().toString().trim());
            editor.putString("servicePath", edtServicePath.getText().toString().trim());
            editor.apply();
            Toast.makeText(this, "Configuration saved", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this, Device_Registration.class);
            startActivity(i);
            finish();
        });
    }


}
