package com.example.neo.convertisseur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Neo on 26/06/2017.
 */

public class MainActivity extends AppCompatActivity {
    private Button imgBtn1;
    private Button imgBtn2;
    private Button imgBtn3;
    private Button imgBtn4;
    private Button imgBtn5;

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgBtn1 = (Button) findViewById(R.id.img_btn1);
        imgBtn1.setText("Corse");
        imgBtn2 = (Button) findViewById(R.id.img_btn2);
        imgBtn2.setText("Guadeloupe");
        imgBtn3 = (Button) findViewById(R.id.img_btn3);
        imgBtn3.setText("Guyane");
        imgBtn4 = (Button) findViewById(R.id.img_btn4);
        imgBtn4.setText("Martinique");
        imgBtn5 = (Button) findViewById(R.id.img_btn5);
        imgBtn5.setText("R\u00e9union");

        imgBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("centre", "Corse");
                startActivity(intent);
            }
        });

        imgBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("centre", "Guadeloupe");
                startActivity(intent);
            }
        });

        imgBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("centre", "Guyane");
                startActivity(intent);
            }
        });

        imgBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("centre", "Martinique");
                startActivity(intent);
            }
        });

        imgBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("centre", "R\u00e9union");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
