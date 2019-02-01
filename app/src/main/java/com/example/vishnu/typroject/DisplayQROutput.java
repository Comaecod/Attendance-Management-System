package com.example.vishnu.typroject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayQROutput extends AppCompatActivity {

    TextView outputQRValue;
    ClipboardManager cbm;
    ClipData cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qroutput);

        outputQRValue = findViewById(R.id.QRValue);

        final String qrDecode = getIntent().getStringExtra("QRValue");

        if (qrDecode.isEmpty())
            outputQRValue.setText("QRCode is Empty!!!");

        outputQRValue.setText(qrDecode);

        cbm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        findViewById(R.id.copyToClipBoard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cd = ClipData.newPlainText("text", qrDecode);
                cbm.setPrimaryClip(cd);
                Toast.makeText(getApplicationContext(), "Message Has Been Copied!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
