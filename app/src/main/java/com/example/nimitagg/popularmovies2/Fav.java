package com.example.nimitagg.popularmovies2;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Fav extends AppCompatActivity implements FavFragment.Calback{
    boolean mTwopane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fav);
        if(findViewById(R.id.detail_layout_container)!=null){
            mTwopane=true;
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_layout_container,new FDetailViewFragment()).commit();
        }
        else
            mTwopane=false;
    }


    @Override
    public void OnItemSelected(Bundle bundle) {
        bundle.putBoolean("pane",mTwopane);
        if(mTwopane){
            FDetailViewFragment f=new FDetailViewFragment();
            f.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_layout_container,f).commit();
        }
        else{
            Intent intent=new Intent(this,Fdetail.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}
