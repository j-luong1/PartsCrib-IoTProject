package com.ceng319.partsCrib;

import android.content.Intent;
import android.os.Bundle;
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

public class OrderDetailActivity extends AppCompatActivity {

    private String selectedOrder;
    private String CurrentUser  = Prevalent.CurrentOnlineUser.getStudent_Number();
    private RequestQueue mQueue;

    private ArrayList<String> orderList = new ArrayList<>();

    private ListView mOrderDetail;
    private ArrayAdapter arrayAdapter;
    private SlidrInterface slidr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent intent = getIntent();

        getSupportActionBar().setTitle(R.string.orders_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedOrder = intent.getStringExtra("Order");
        mOrderDetail = (ListView)findViewById(R.id.order_ListView);
        mQueue = Volley.newRequestQueue(this);
        slidr = Slidr.attach(this);
        jsonParse();

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
    }

    private void jsonParse(){

        String url = "http://apollo.humber.ca/~n01267335/CENG319/order.php?TID=" + selectedOrder + "&username=" + CurrentUser;
        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Category");
                            JSONArray jsonArray2 = response.getJSONArray("Name");
                            JSONArray jsonArray3 = response.getJSONArray("SID");
                            JSONArray jsonArray4 = response.getJSONArray("Quantity");
                            for (int i = 0; i< jsonArray.length(); i++){
                                //Append to array list to create list view
                                orderList.add(jsonArray.get(i)+": "+jsonArray2.get(i)+"\nSID:"+jsonArray3.get(i)+"    Quantity:"+jsonArray4.get(i));
                                mOrderDetail.setAdapter(arrayAdapter);
                                arrayAdapter.clear();
                                arrayAdapter.addAll(orderList);
                                arrayAdapter.notifyDataSetChanged();
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
