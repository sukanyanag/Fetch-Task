package com.example.fetchtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemsAdapter(itemList);
        recyclerView.setAdapter(adapter);

        fetchData();
    }

    private void fetchData() {

        String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a JSON response from the provided URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Parsing JSON array
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            // Extracting fields from JSON object
                            int id = jsonObject.getInt("id");
                            int listId = jsonObject.getInt("listId");
                            String name = jsonObject.optString("name", "");

                            // Filter out items where "name" is blank or null
                            if (!name.trim().isEmpty()) {
                                // Add item to the list
                                itemList.add(new Item(id, listId, name));
                            }
                        }

                        // Sort the itemList
                        Collections.sort(itemList, (item1, item2) -> {
                            // First by listId
                            if (item1.getListId() != item2.getListId()) {
                                return Integer.compare(item1.getListId(), item2.getListId());
                            }
                            // Then by name
                            return item1.getName().compareToIgnoreCase(item2.getName());
                        });

                        // Notify adapter about the new data
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle the JSON exception
                    }
                },
                error -> {
                    // Handle the error
                });

        // Add the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }
}