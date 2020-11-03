package com.example.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=20";
    private TextView emptyView;
    private EarthquakeAdapter adapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private ProgressBar progressBar;
    private TextView loading;
    private ConnectivityManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView ls = (ListView) findViewById(R.id.list);
         adapter = new EarthquakeAdapter(this,new ArrayList<Earthquake>());
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
             Earthquake current=adapter.getItem(position);
                Uri quakeUri=Uri.parse(current.getUrl());
                Intent intent=new Intent(Intent.ACTION_VIEW,quakeUri);
                startActivity(intent);
            }
        });
        manager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        manager.getA
     volleyCall();

    }
    private void volleyCall(){
        RequestQueue queue = Volley.newRequestQueue(this);
       progressBar=(ProgressBar)findViewById(R.id.pBar);
       loading=(TextView)findViewById(R.id.loading);
       progressBar.setVisibility(View.VISIBLE);
       loading.setVisibility(View.VISIBLE);
// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, USGS_REQUEST_URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        ArrayList<Earthquake> earthquakes=QueryUtils.extractFeatureFromJson(response);
                        adapter.addAll(earthquakes);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("volley","volley error");

            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
