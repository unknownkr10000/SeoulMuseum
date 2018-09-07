package com.example.in_301.seoulmuseum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayList(View v) {
        int id = v.getId();
        Intent it = new Intent(this, MuseumList.class);
        startActivity(it);
    }

    public void displayMap(View v) {
        int id = v.getId();
        Intent it = new Intent(this, MapsActivity.class);
        startActivity(it);
    }
}
