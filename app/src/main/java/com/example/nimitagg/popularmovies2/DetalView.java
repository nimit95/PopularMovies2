package com.example.nimitagg.popularmovies2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DetalView extends AppCompatActivity {

    TextView tt1,tt2,tt3,tt5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal_view);
        tt1=(TextView)findViewById(R.id.title);
        tt2=(TextView)findViewById(R.id.textView2);
        tt3= (TextView) findViewById(R.id.textView3);
        tt5= (TextView) findViewById(R.id.textView5);
        Intent intent= getIntent();
        String JsonData=intent.getStringExtra("JsonStr");
        String link=intent.getStringExtra("link");
       // Log.e("here", JsonData);
      //  Log.e("here2",link);
        try {
            JSONObject data=new JSONObject(JsonData);
            JSONArray JsonArray = data.getJSONArray("results");
            for(int i=0;i<JsonArray.length();i++)
            {
                JSONObject Data =JsonArray.getJSONObject(i);
                if(Data.getString("poster_path").compareTo(link)==0){
                    tt1.setText(Data.getString("original_title"));
                  //  Log.e("th8is", "http://image.tmdb.org/t/p/w185/" + Data.getString("poster_path"));
                    Picasso.with(DetalView.this).load("http://image.tmdb.org/t/p/w185/" + Data.getString("poster_path")).into((ImageView) findViewById(R.id.imageView2));
                    tt2.setText(Data.getString("release_date"));
                    if(Data.getString("overview").compareTo("")!=0)
                    tt3.setText(Data.getString("overview"));
                    else {
                        tt3.setText("No Overview Found");
                    }
                    tt5.setText(Data.getString("vote_average")+"/10");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
