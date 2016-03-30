package com.example.nimitagg.popularmovies2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Fdetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal_view);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.detail_layout_container, new FDetailViewFragment()).commit();
        }
    }
}
