package com.example.nimitagg.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class reviews extends AppCompatActivity {
    ListView l1;
    private String APIKEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Intent intent=getIntent();
        APIKEY=intent.getStringExtra("api");

        new Data(getApplicationContext()).execute(intent.getStringExtra("ID"));


    }
    public class Data extends AsyncTask<String,Void,Object[]> {
        private Context mContext;

        public Data(Context context) {
            mContext = context;
        }

        @Override
        protected void onPostExecute(Object[] object) {
           // super.onPostExecute(object);
            String[] str1= (String[])  object[0];
            String[] str2= (String[]) object[1];
           // Log.e("ki",str[0]);
            for(int i=0;i<str2.length;i++)
                str2[i]=str1[i]+" :- \n\n"+str2[i];
            l1= (ListView) findViewById(R.id.listView2);
            ArrayAdapter<String> arr=new ArrayAdapter<String>(getApplicationContext(),R.layout.reviews, R.id.textView9,str2);
          l1.setAdapter(arr);
        }

        @Override
        protected Object[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

// Will contain the raw JSON response as a string.
            String dataJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url;

                url = new URL("http://api.themoviedb.org/3/movie/"+params[0]+"/reviews?api_key=" + APIKEY);

               // Log.e("this", url + "");

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
            } finally {
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
               // Log.e("hi",dataJsonStr);
                return getTrailerData(dataJsonStr);


            } catch (Exception e) {
                //   Log.e("error",e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }

    }
    private Object[] getTrailerData(String JsonData) throws JSONException {

        JSONObject PicJson=new JSONObject(JsonData);
        JSONArray resultarray = PicJson.getJSONArray("results");
        String[] str1=new String[resultarray.length()];
        String[] str2=new String[resultarray.length()];
        for(int i=0;i<resultarray.length();i++){
            JSONObject PosterPath = resultarray.getJSONObject(i);
            str1[i]=PosterPath.getString("author");
            str2[i]=PosterPath.getString("content");
        }
      /*  for(String s:PicResult){
            Log.v("pic code", s );
        }*/
        return new Object[]{str1,str2};
    }
}
