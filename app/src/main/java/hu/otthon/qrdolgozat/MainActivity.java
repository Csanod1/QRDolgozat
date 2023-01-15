package hu.otthon.qrdolgozat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private Button scanButton;
    private Button mentButton;
    private TextView textView;

    private DBHelper adatbázis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator =
                        new IntentIntegrator(MainActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null ) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Visszaléptél a scannelésből", Toast.LENGTH_SHORT).show();
            } else {
                textView.setText(result.getContents());
                Uri uri = Uri.parse(result.getContents());

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void init(){
        scanButton = findViewById(R.id.scanButton);
        mentButton = findViewById(R.id.mentButton);
        textView = findViewById(R.id.textView);
    }
}