package com.example.administrator.testapplication;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button1:
                    startActivity(new Intent(MainActivity.this, Activity1.class));
                    break;
                case R.id.button2:
                    startActivity(new Intent(MainActivity.this, Activity2.class));
                    break;
                case R.id.button3:
                    Intent intent = new Intent();
                    intent.setAction("staticReceiver");
                    sendBroadcast(intent);
                default:
                    break;

            }
        }
    };
}
