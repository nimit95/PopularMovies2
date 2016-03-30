package com.example.nimitagg.popularmovies2;


import android.content.Context;

import android.database.Cursor;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


/**

 */
public class FavFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageListAdapter imageAdapter;
    cursorAdapter cur;
    GridView gridView;
    Cursor cursor;
    Bundle bundle;
    String[] result;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static FavFragment newInstance(String param1, String param2) {
        FavFragment fragment = new FavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public interface Calback{
        public void OnItemSelected(Bundle bundle);
    }
    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        gridView= (GridView)view.findViewById(R.id.gridView);

        bundle=new Bundle();
        cursor=getActivity().getContentResolver().query(TestTable.CONTENT_URI,null,null,null,null);
        /*cursor.setNotificationUri(getContentResolver(),TestTable.CONTENT_URI);*/
        getLoaderManager().initLoader(0, null, this);
 /*       result=new String[cursor.getCount()];
        if(cursor.moveToFirst()){
            do{
                result[cursor.getPosition()]=cursor.getString(1);
            }while (cursor.moveToNext());
        }*/
       /* imageAdapter=new ImageListAdapter(getApplicationContext(), result);*/
        cur=new cursorAdapter(getActivity(),cursor,0);
        gridView.setAdapter(cur);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String data = imageAdapter.getItem(position);
                //oast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();
                //startActivity(intent.putExtra("link", data.substring(31)));
                cursor.moveToPosition(position);
                bundle.putString("title", cursor.getString(1));
                bundle.putString("movieid", cursor.getString(2));
                bundle.putString("poster", cursor.getString(3));
                bundle.putString("overview", cursor.getString(4));
                bundle.putString("rating", cursor.getString(5));
                bundle.putString("release", cursor.getString(6));
             //   cursor.close();
                ((Calback)getActivity()).OnItemSelected(bundle);
           /*     */
            }
        });
        return view;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader=new CursorLoader(getActivity(),TestTable.CONTENT_URI,null,null,null,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cur.swapCursor(data);

    }
    @Override
    public void onResume() {
        super.onResume();
        //getApplicationContext().getContentResolver().notifyDa(TestTable.CONTENT_URI,null);
        //  cur.notifyDataSetChanged();
        cursor=getActivity().getContentResolver().query(TestTable.CONTENT_URI,null,null,null,null);
        //cur.swapCursor(cursor);
        // cur.notifyDataSetChanged();
        /*getSupportLoaderManager().restartLoader(0, null, this);*/
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cur.swapCursor(null);

    }



    public class cursorAdapter extends CursorAdapter {
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
