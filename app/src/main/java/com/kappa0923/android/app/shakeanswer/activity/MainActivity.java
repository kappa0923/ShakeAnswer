package com.kappa0923.android.app.shakeanswer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kappa0923.android.app.shakeanswer.R;

/**
 * アプリの起動を管理するクラス
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
