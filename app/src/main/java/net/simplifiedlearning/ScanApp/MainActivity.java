package net.simplifiedlearning.ScanApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Button generateButton,operation1Button,operation2Button,operation3Button,operation4Button;
    public static String randomCode;
    TextView codeText;
    ImageView imageBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        codeText = findViewById(R.id.codeTextView);
        generateButton =findViewById(R.id.generateButton);
        operation1Button = findViewById(R.id.operation1Button);
        operation2Button = findViewById(R.id.operation2Button);
        operation3Button = findViewById(R.id.operation3Button);
        operation4Button = findViewById(R.id.operation4Button);
        imageBarcode = findViewById(R.id.imageView);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomCode =generateRadomCode(12);
                codeText.setText(randomCode);

//                Bitmap bitmap = null;
//                try {
//
//                    bitmap = encodeAsBitmap(randomCode, BarcodeFormat.CODE_128, 600, 300);
//                    imageBarcode.setImageBitmap(bitmap);
//
//                } catch (WriterException e) {
//                    e.printStackTrace();
//                }

            }
        });

        operation1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityOperation1();
            }
        });
        operation2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityOperation2();
            }
        });
        operation3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityOperation3();
            }
        });
        operation4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityOperation4();
            }
        });

    }

    private void openActivityOperation1(){
        Intent intent = new Intent(this,Operation1.class);
        startActivity(intent);
    }
    private void openActivityOperation2(){
        Intent intent = new Intent(this,Operation2.class);
        startActivity(intent);
    }
    private void openActivityOperation3(){
        Intent intent = new Intent(this,Operation3.class);
        startActivity(intent);
    }
    private void openActivityOperation4(){
        Intent intent = new Intent(this,Operation4.class);
        startActivity(intent);
    }

    private String generateRadomCode(int Lengtht){
        String temp="";

        for (int i =0;i<Lengtht;i++){
            temp += (new Random().nextInt((9 - 0) + 1));
        }

        return temp;
    }

}
