package com.example.fetchtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MainActivity of the application.
 * This activity is responsible for displaying a list of items in a RecyclerView.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private List<Item> itemList = new ArrayList<>();
    private ProgressBar progressBar;

    /**
     * Called when the activity is starting.
     * This is where most initialization goes: calling setContentView(int) to inflate the activity's UI,
     * using findViewById(int) to programmatically interact with widgets in the UI, calling setAdapter on a RecyclerView, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle
     *                           contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fetch Task");

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progressBar);

        adapter = new ItemsAdapter(itemList);
        recyclerView.setAdapter(adapter);

        fetchData();
    }

    /**
     * Fetches data from a specified URL and populates the RecyclerView.
     * This method sends a network request to fetch JSON data, parses the response,
     * and updates the UI with the parsed data. It handles both successful and failed network requests.
     */
    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

        // Creating a request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Requesting a JSON response from the provided URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    // Processing in a background thread
                    new Thread(() -> {
                        try {
                            // Clear existing data
                            itemList.clear();

                            // Parsing JSON array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                // Extracting fields from JSON object
                                int id = jsonObject.getInt("id");
                                int listId = jsonObject.getInt("listId");
                                String name = jsonObject.optString("name", "");

                                // Check for both actual null, the string "null", empty, and whitespace only
                                if (name == null || "null".equals(name) || name.trim().isEmpty()) {
                                    continue;
                                }

                                // Adding item to the list
                                itemList.add(new Item(id, listId, name));
                            }

                            // Sorting the itemList
                            Collections.sort(itemList, (item1, item2) -> {
                                // First by listId
                                if (item1.getListId() != item2.getListId()) {
                                    return Integer.compare(item1.getListId(), item2.getListId());
                                }
                                // Then by name
                                return item1.getName().compareToIgnoreCase(item2.getName());
                            });

                            for (Item item : itemList) {
                                Log.d("MainActivity", "Filtered Item: " + item.getName());
                            }

                            // Updating the UI on the main thread after processing
                            runOnUiThread(() -> {
                                adapter.notifyDataSetChanged();
                                // Hide progress bar on successful fetch
                                progressBar.setVisibility(View.GONE);
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                        }
                    }).start();
                },
                error -> {
                    // Handling the error
                    String errorMessage = error instanceof NetworkError ? "Network error!" : "Unexpected error!";
                    Snackbar.make(findViewById(R.id.main_layout), errorMessage, Snackbar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                });

        // Adding the request to the RequestQueue
        queue.add(jsonArrayRequest);
    }
}