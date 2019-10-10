package net.simplifiedlearning.ScanApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class Operation2 extends AppCompatActivity {

    Button cancelButton;
    TextView codeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation2);

        codeText = (TextView) findViewById(R.id.barcodeTextView);
        String code= MainActivity.randomCode;
        codeText.setText(code);
    }
}
