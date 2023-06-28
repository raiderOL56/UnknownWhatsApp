package com.example.unknownwhatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText phoneNumberInput, messageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberInput = findViewById(R.id.noTelefonico);
        messageInput = findViewById(R.id.mensaje);
    }

    public void SendMessage(View view) {
        String phoneNumber = phoneNumberInput.getText().toString();
        String message = messageInput.getText().toString();

        if (InputIsEmpty(phoneNumber)) {
            phoneNumberInput.setError("Este campo es obligatorio");
        } else {
            if (AppInstalledOrNot("com.whatsapp")) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);

                if (InputIsEmpty(message)) {
                    sendIntent.setData(Uri.parse("whatsapp://send?phone=521" + phoneNumber));
                } else {
                    sendIntent.setData(Uri.parse("whatsapp://send?phone=521" + phoneNumber + "&text=" + message));
                }
                startActivity(sendIntent);
            } else {
                Toast.makeText(this, "WhatsApp no está instalado en este dispositivo.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean InputIsEmpty(String value) {
        return value.trim().isEmpty();
    }

    private boolean AppInstalledOrNot(String url) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}