package org.greenleaf.utils.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenleaf.utils.AssetsUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                AssetsUtils.asset2String(getApplicationContext(), "data.xml");
            }
        }).start();
        new InjectedClsss();
    }
}
