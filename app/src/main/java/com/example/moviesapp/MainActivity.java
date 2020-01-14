package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesapp.adapter.Data;
import com.example.moviesapp.adapter.MovieAdapter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView txtShowTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtShowTextResult = findViewById(R.id.txtDisplay);
        final ListView list = findViewById(R.id.list);
        final ArrayList<Data> arrayList = new ArrayList<Data>();


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://desafio-mobile.nyc3.digitaloceanspaces.com/movies";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    StringBuilder formattedResult = new StringBuilder();
                    JSONArray responseJSONArray = response;

                    for (int i = 0; i < responseJSONArray.length(); i++) {
                        JSONObject obj = responseJSONArray.getJSONObject((i));
                        arrayList.add(new Data (obj.get("title").toString(), obj.get("id").toString(), obj.get("poster_url").toString()));
                        formattedResult.append("\n" + obj.get("title"));
                    }



                    txtShowTextResult.setText("Movies \n" + formattedResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtShowTextResult.setText("An Error occured while making the request");
            }
        });
        requestQueue.add(jsonArrayRequest);

        MovieAdapter movieAdapter = new MovieAdapter(this, arrayList);
        list.setAdapter(movieAdapter);

    }
}