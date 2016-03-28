package com.example.nimitagg.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Calback {
    public boolean mTwoPane;
    public boolean mod=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!isNetworkAvailable()){
            Intent intent=new Intent(MainActivity.this,Fav.class);
            startActivity(intent);
        }
        else{
            if(findViewById(R.id.detail_layout_container)!=null){
                if(savedInstanceState==null){
                    mTwoPane=true;
                    Log.e("yo","yo");
                    getSupportFragmentManager().beginTransaction().replace(R.id.detail_layout_container,new DetailViewFragment()).commit();
                }

            }
            else
                mTwoPane=false;
        }

    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void OnItemSelected(Bundle bundle) {
        if(mTwoPane){
            DetailViewFragment d=new DetailViewFragment();
            d.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_layout_container,d).commit();
        }
        else{
            Intent intent=new Intent(this,DetalView.class);
            startActivity(intent.putExtras(bundle));
        }
    }
}