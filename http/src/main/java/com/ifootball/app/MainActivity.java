package com.ifootball.app;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ifootball.app.http.BetterHttp;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);

    }

    public void connection(View v) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = BetterHttp.get("http://192.168.60.101:9100/topic/query?latitude=30.560732&longitude=104.051094&listtype=1&pageindex=1&pagesize=10");
                    Log.i("Request:", s);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

//        tv.setText(s);
    }


}
