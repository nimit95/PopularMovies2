package com.example.nimitagg.popularmovies2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class Fav extends AppCompatActivity {
    ImageListAdapter imageAdapter;
    GridView gridView;
    String[] result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        gridView= (GridView)findViewById(R.id.gridView);
        final Intent intent=new Intent(Fav.this,Fdetail.class);
        final Cursor cursor=getContentResolver().query(TestTable.CONTENT_URI,null,null,null,null);
        result=new String[cursor.getCount()];
        if(cursor.moveToFirst()){
            do{
                result[cursor.getPosition()]=cursor.getString(1);
            }while (cursor.moveToNext());
        }
        imageAdapter=new ImageListAdapter(getApplicationContext(), result);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = imageAdapter.getItem(position);
                //oast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();
                //startActivity(intent.putExtra("link", data.substring(31)));
                cursor.moveToPosition(position);
                intent.putExtra("title",cursor.getString(0));
                intent.putExtra("poster",cursor.getString(1));
                intent.putExtra("overview",cursor.getString(2));
                intent.putExtra("rating",cursor.getString(3));
                intent.putExtra("release",cursor.getString(4));
                cursor.close();
                startActivity(intent);
            }
        });
    }


}
