package com.ceng319.partsCrib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity{
    private RequestQueue mQueue;
    private ArrayList<String> itemList = new ArrayList<>();
    private ArrayList<String> sidList = new ArrayList<>();
    private ListView mListItems;
    private ArrayAdapter arrayAdapter;
    private String selectedCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Intent intent = getIntent();
        selectedCat = intent.getStringExtra("Category");
        Toast.makeText(ItemListActivity.this,"clicked item: "+ selectedCat,Toast.LENGTH_SHORT).show();

        mListItems = (ListView)findViewById(R.id.listViewItems);

        mQueue = Volley.newRequestQueue(this);
        jsonParse();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);

        mListItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = sidList.get(i);
                Intent newIntent = new Intent(ItemListActivity.this,ItemActivity.class);
                newIntent.putExtra("Item",selectedItem);
                ItemListActivity.this.startActivity(newIntent);
            }
        });

    }
    private void jsonParse(){

        String url = "http://munro.humber.ca/~n01267335/CENG319/query.php?category="+selectedCat;

        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Items");
                            for (int i = 0; i< jsonArray.length(); i++){
                                //Append to array list to create list view
                                itemList.add(String.valueOf(jsonArray.get(i)));

                                mListItems.setAdapter(arrayAdapter);
                                arrayAdapter.clear();
                                arrayAdapter.addAll(itemList);
                                arrayAdapter.notifyDataSetChanged();


                            }

                            JSONArray jsonArray2 = response.getJSONArray("SID");
                            for (int i = 0; i< jsonArray.length(); i++){
                                sidList.add(String.valueOf(jsonArray2.get(i)));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }
}
