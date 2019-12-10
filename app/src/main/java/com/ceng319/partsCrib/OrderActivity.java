package com.ceng319.partsCrib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ceng319.partsCrib.Prevalent.Prevalent;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private ArrayList <String> orderList = new ArrayList<>();
    private ArrayList<String> tidList = new ArrayList<>();
    private ListView mListCategories;
    private ArrayAdapter arrayAdapter;
    private String CurrentUser  = Prevalent.CurrentOnlineUser.getStudent_Number();
    private SlidrInterface slidr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setTitle(R.string.orders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListCategories = (ListView)findViewById(R.id.order_ListView);

        mQueue = Volley.newRequestQueue(this);
        slidr = Slidr.attach(this);
        jsonParse();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);

        mListCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedOrder = tidList.get(i);
                Intent newIntent = new Intent(OrderActivity.this, OrderDetailActivity.class);
                newIntent.putExtra("Order",selectedOrder);
                startActivity(newIntent);
            }
        });
    }

    private void jsonParse(){

        String url = "http://apollo.humber.ca/~n01267335/CENG319/order.php?username=" + CurrentUser;
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("TOut");
                        for (int i = 0; i< jsonArray.length(); i++){
                            //Append to array list to create list view
                            orderList.add(String.valueOf(jsonArray.get(i)));
                            mListCategories.setAdapter(arrayAdapter);
                            arrayAdapter.clear();
                            arrayAdapter.addAll(orderList);
                            arrayAdapter.notifyDataSetChanged();
                        }

                        JSONArray jsonArray2 = response.getJSONArray("TID");
                        for (int i = 0; i< jsonArray.length(); i++){
                            tidList.add(String.valueOf(jsonArray2.get(i)));
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
