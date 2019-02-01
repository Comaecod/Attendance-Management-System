package com.example.vishnu.typroject;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQRCode extends AppCompatActivity {

    EditText qrText;
    Button genQR;
    ImageView qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        qrText = findViewById(R.id.qrtext);
        genQR = findViewById(R.id.genqrcode);
        qrCode = findViewById(R.id.qrcodedisplay);

        genQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text2qr = qrText.getText().toString().trim();

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                try{

                    BitMatrix bitMatrix = multiFormatWriter.encode(text2qr, BarcodeFormat.QR_CODE,250,250);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qrCode.setImageBitmap(bitmap);
                    qrCode.requestFocus();

                }

                catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}
