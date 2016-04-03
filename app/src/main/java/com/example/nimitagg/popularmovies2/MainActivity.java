package com.example.nimitagg.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Calback {
    public boolean mTwoPane;
    public boolean mod=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!isNetworkAvailable()){
           // Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,Fav.class);
            startActivity(intent);
           // Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
        else{
            if(findViewById(R.id.detail_layout_container)!=null){
                mTwoPane=true;
                if(savedInstanceState==null){
                   // Log.e("yo","yo");
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
    public void onItemSelected(Bundle bundle) {
        if(mTwoPane){
            DetailViewFragment d=new DetailViewFragment();
            d.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_layout_container,d).commit();
        }
        else{
            Intent intent=new Intent(this,DetalView.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}