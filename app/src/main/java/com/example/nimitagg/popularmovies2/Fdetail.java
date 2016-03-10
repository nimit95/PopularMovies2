package com.example.nimitagg.popularmovies2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Fdetail extends AppCompatActivity {
    TextView tt1,tt2,tt3,tt5;
    TextView tt4;
    Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal_view);
        tt1=(TextView)findViewById(R.id.title);
        tt2=(TextView)findViewById(R.id.textView2);
        tt3= (TextView) findViewById(R.id.textView3);
        tt5= (TextView) findViewById(R.id.textView5);
        btn1= (Button) findViewById(R.id.button);
        btn2= (Button) findViewById(R.id.button2);
        Intent intent= getIntent();
        btn1.setText("REMOVE AS\nFAVORITE");
        Picasso.with(Fdetail.this).load(intent.getStringExtra("poster")).into((ImageView) findViewById(R.id.imageView2));
        tt1.setText(intent.getStringExtra("title"));
        tt2.setText(intent.getStringExtra("release"));
        if(intent.getStringExtra("overview").compareTo("")!=0)
            tt3.setText(intent.getStringExtra("overview"));

        else {
            tt3.setText("No Overview Found");
        }
        tt5.setText(intent.getStringExtra("rating") + "/10");
        btn2.setVisibility(View.GONE);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
