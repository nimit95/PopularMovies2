package com.example.nimitagg.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Fav extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    ImageListAdapter imageAdapter;
    cursorAdapter cur;
    GridView gridView;
    Cursor cursor;
    String[] result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        gridView= (GridView)findViewById(R.id.gridView);
        final Intent intent=new Intent(Fav.this,Fdetail.class);

        cursor=getContentResolver().query(TestTable.CONTENT_URI,null,null,null,null);
        /*cursor.setNotificationUri(getContentResolver(),TestTable.CONTENT_URI);*/
        getSupportLoaderManager().initLoader(0, null, this);
 /*       result=new String[cursor.getCount()];
        if(cursor.moveToFirst()){
            do{
                result[cursor.getPosition()]=cursor.getString(1);
            }while (cursor.moveToNext());
        }*/
       /* imageAdapter=new ImageListAdapter(getApplicationContext(), result);*/
        cur=new cursorAdapter(getApplicationContext(),cursor,0);
        gridView.setAdapter(cur);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String data = imageAdapter.getItem(position);
                //oast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();
                //startActivity(intent.putExtra("link", data.substring(31)));
                cursor.moveToPosition(position);
                intent.putExtra("title",cursor.getString(1));
                intent.putExtra("movieid",cursor.getString(2));
                intent.putExtra("poster",cursor.getString(3));
                intent.putExtra("overview",cursor.getString(4));
                intent.putExtra("rating",cursor.getString(5));
                intent.putExtra("release", cursor.getString(6));
                cursor.close();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getApplicationContext().getContentResolver().notifyDa(TestTable.CONTENT_URI,null);
      //  cur.notifyDataSetChanged();
          cursor=getContentResolver().query(TestTable.CONTENT_URI,null,null,null,null);
        //cur.swapCursor(cursor);
       // cur.notifyDataSetChanged();
        /*getSupportLoaderManager().restartLoader(0, null, this);*/
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader=new CursorLoader(getApplicationContext(),TestTable.CONTENT_URI,null,null,null,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cur.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cur.swapCursor(null);

    }

    public class cursorAdapter extends CursorAdapter{
        public cursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.image,parent,false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Picasso
                    .with(context)
                    .load(cursor.getString(3))
                    .networkPolicy(NetworkPolicy.OFFLINE)  // will explain later
                    .into((ImageView) view.findViewById(R.id.imageView));
        }
    }
}
