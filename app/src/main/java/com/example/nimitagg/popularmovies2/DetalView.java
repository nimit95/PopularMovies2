package com.example.nimitagg.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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


public class DetalView extends AppCompatActivity {

    TextView tt1,tt2,tt3,tt5;
    private String APIKEY;
    ImageButton im1;
    TextView tt4;
    Button btn1;
    TrailerAdapter trail;
    ListView lt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal_view);
        tt1=(TextView)findViewById(R.id.title);
        final Intent intent1=new Intent(DetalView.this,reviews.class);
        tt2=(TextView)findViewById(R.id.textView2);
        tt3= (TextView) findViewById(R.id.textView3);
        tt5= (TextView) findViewById(R.id.textView5);
        btn1= (Button) findViewById(R.id.button);
        Intent intent= getIntent();
        APIKEY=intent.getStringExtra("api");
        String JsonData=intent.getStringExtra("JsonStr");
        lt1= (ListView) findViewById(R.id.listView);
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
                    new Data(getApplicationContext()).execute(Data.getString("id"));
                    intent1.putExtra("ID",Data.getString("id"));
                    intent1.putExtra("api",APIKEY);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent1);
            }
        });
    }
    public class Data extends AsyncTask<String,Void,String[]> {
        private Context mContext;

        public Data(Context context) {
            mContext = context;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            trail=new TrailerAdapter(getApplicationContext(),strings);
            lt1.setAdapter(trail);
        }

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

// Will contain the raw JSON response as a string.
            String dataJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url;

                    url = new URL("http://api.themoviedb.org/3/movie/"+params[0]+"/trailers?api_key=" + APIKEY);

                    Log.e("this",url+"");

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
                Log.e("hi",dataJsonStr);
                return getTrailerData(dataJsonStr);


            } catch (Exception e) {
                //   Log.e("error",e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }

    }
    private String[] getTrailerData(String JsonData) throws JSONException {

        JSONObject PicJson=new JSONObject(JsonData);
        JSONArray youtubearray = PicJson.getJSONArray("youtube");
        String[] trailerResult=new String[youtubearray.length()];
        for(int i=0;i<youtubearray.length();i++){
            JSONObject PosterPath = youtubearray.getJSONObject(i);
            trailerResult[i]="https://www.youtube.com/watch?v="+PosterPath.getString("source");
        }
      /*  for(String s:PicResult){
            Log.v("pic code", s );
        }*/
        return trailerResult;
    }
    private class TrailerAdapter extends ArrayAdapter{
        private Context context;
        private String[] Trailers;
        private LayoutInflater inflater;
        public TrailerAdapter(Context context, String[] Trailers) {
            super(context, R.layout.trailer,Trailers);
            this.context=context;
            this.Trailers=Trailers;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView= inflater.inflate(R.layout.trailer,null,true);
            }
            tt4=(TextView)convertView.findViewById(R.id.textView7);
            tt4.setText("Trailer" + (position+1));
            im1= (ImageButton) convertView.findViewById(R.id.imageButton);
            im1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Trailers[position])));
                }
            });
            tt4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Trailers[position])));
                }
            });
            return convertView;
        }

        @Override
        public String getItem(int position) {
            return Trailers[position];
        }
    }
}
