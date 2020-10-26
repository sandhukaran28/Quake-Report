package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();
        ListView ls = (ListView) findViewById(R.id.list);
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);
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
    }
}