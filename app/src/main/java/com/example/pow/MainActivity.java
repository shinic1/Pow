package com.example.pow;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SkiResortAdapter adapter;
    private List<SkiResort> skiResorts;
    private List<SkiResort> filteredSkiResorts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        SearchView searchView = findViewById(R.id.searchView);

        skiResorts = new ArrayList<>();
        loadSkiResortsFromJSON(); // Load JSON data into skiResorts class

        // Sort the ski resorts by name
        Collections.sort(skiResorts, new Comparator<SkiResort>() {
            @Override
            public int compare(SkiResort resort1, SkiResort resort2) {
                return resort1.getName().compareToIgnoreCase(resort2.getName());
            }
        });

        //search
        filteredSkiResorts = new ArrayList<>(skiResorts);

        adapter = new SkiResortAdapter(filteredSkiResorts, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSkiResorts(newText);
                return true;
            }
        });
    }

    private void loadSkiResortsFromJSON() {
        try {
            // Read JSON file from ski_resorts.json
            InputStreamReader is = new InputStreamReader(getAssets().open("ski_resorts.json"));
            BufferedReader reader = new BufferedReader(is);
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            // Parse JSON data
            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String location = jsonObject.getString("location");
                String description = jsonObject.getString("description"); // Get the description
                skiResorts.add(new SkiResort(name, location, description));
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Error loading JSON", e);
        }
    }

    private void filterSkiResorts(String query) {
        //Populate array
        filteredSkiResorts.clear();
        for (SkiResort resort : skiResorts) {
            if (resort.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredSkiResorts.add(resort);
            }
        }
        adapter.updateList(filteredSkiResorts);
    }
}