package com.example.nimitagg.popularmovies2;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.GridView;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public String APIKEY;
    public int mPosition;

    public interface Calback{
        public void OnItemSelected(Bundle bundle);
    }
    public MainActivityFragment() {
    }
    //public Bundle bundle;
    ImageListAdapter imageAdapter;
    GridView gridView;
    Bundle bundle;
    @Override
    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setHasOptionsMenu(true);
    }
    Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        APIKEY="";
        rating GetData = new rating(getActivity());
        bundle=new Bundle();
        gridView= (GridView)view.findViewById(R.id.gridView);
        this.intent=new Intent(getActivity(),DetalView.class);
        bundle.putString("api", APIKEY);
        GetData.execute(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String data=imageAdapter.getItem(position);
                //oast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();
                bundle.putString("link", data.substring(31));
                ((Calback)getActivity()).OnItemSelected(bundle);
                mPosition=position;
              //startActivity(intent.putExtras(bundle));
            }
        });
        if(savedInstanceState!=null && savedInstanceState.containsKey("pos")){
            mPosition=savedInstanceState.getInt("pos",0);
            gridView.setSelection(mPosition);
        }
        return view;
    }
    public class rating extends AsyncTask<Boolean,Void,String[]>{
        private Context mContext;
        public rating (Context context){
            mContext = context;
        }
        @Override
        protected String[] doInBackground(Boolean... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

// Will contain the raw JSON response as a string.
            String dataJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url;
                if(params[0]==true)
                    url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + APIKEY);
                else
                    url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=" + APIKEY);


                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    dataJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    dataJsonStr = null;
                }
                dataJsonStr = buffer.toString();
            } catch (IOException e) {
               // Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                dataJsonStr = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                       // Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
           // Log.v("here",dataJsonStr);

            try {

                return getPicData(dataJsonStr);

            }
            catch (JSONException e){
             //   Log.e("error",e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }

       @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            /*imageAdapter=new ImageListAdapter(getActivity(),result);
           gridView.setAdapter(imageAdapter);*/
           //gridView.setAdapter(new ImageListAdapter(mContext, result));
           imageAdapter=new ImageListAdapter(getActivity(), result);
           gridView.setAdapter(imageAdapter);
        }
    }
    private String[] getPicData(String JsonData) throws JSONException {
       bundle.putString("JsonStr", JsonData);
        JSONObject PicJson=new JSONObject(JsonData);
        JSONArray resultarray = PicJson.getJSONArray("results");
        String[] PicResult=new String[resultarray.length()];
        for(int i=0;i<resultarray.length();i++){
            JSONObject PosterPath = resultarray.getJSONObject(i);
            PicResult[i]="http://image.tmdb.org/t/p/w185/"+PosterPath.getString("poster_path");
        }
      /*  for(String s:PicResult){
            Log.v("pic code", s );
        }*/
        return PicResult;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        //return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mPosition!=gridView.INVALID_POSITION){
            outState.putInt("pos",mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id==R.id.action_settings1){
            new rating(getActivity()).execute(true);
        }
        if (id==R.id.action_settings2){
            new rating(getActivity()).execute(false);
        }
        if (id==R.id.action_settings3){
            Intent intent=new Intent(getActivity(),Fav.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
