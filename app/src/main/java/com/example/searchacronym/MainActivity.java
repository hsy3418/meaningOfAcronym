package com.example.searchacronym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sendRequest(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();


                return false;
            }
        });


    }


    /**
     * Create a request sender to fetch the data based on search Query
     */
    private void sendRequest(String query){
        String url = "http://10.0.2.2:8080/acronym/name/"+query;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    String name;
                    String meaning;
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            name = response.getString("acronymName");
                            meaning = response.getString("acronymMeaning");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getBaseContext(), meaning, Toast.LENGTH_LONG).show();
                        //textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

    // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
