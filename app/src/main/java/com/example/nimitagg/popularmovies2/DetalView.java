package com.example.nimitagg.popularmovies2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.Inflater;

import ckm.simple.sql_provider.processor.internal.Table;

public class DetalView extends AppCompatActivity {
    DetailViewFragment d=new DetailViewFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal_view);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.detail_layout_container,d).commit();
        }

       // Log.e("here", JsonData);
      //  Log.e("here2",link);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuItem shareItem = menu.findItem(R.id.action_share);
        getMenuInflater().inflate(R.menu.share, menu);
       // Log.e("chor","chor"+d.getfirst());
        ShareActionProvider myShareActionProvider =
                new ShareActionProvider(getApplicationContext());
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        if(!d.getfirst().equals("")) {
            myShareIntent.putExtra(Intent.EXTRA_TEXT, "View this trailer on " + d.getfirst());
            myShareIntent.setType("text/plain");
            myShareActionProvider.setShareIntent(myShareIntent);
            MenuItemCompat.setActionProvider(shareItem, myShareActionProvider);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share && d.getfirst().equals("")) {
            Toast.makeText(getApplicationContext(),"Still fetching data or no trailer availabe",Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
