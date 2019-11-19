package com.ceng319.partsCrib;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceng319.partsCrib.Prevalent.Prevalent;
import com.ceng319.partsCrib.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    public static final String SHARED_PREFS = Prevalent.CurrentOnlineUser.getStudent_Number();
    private ArrayList<ItemHandler> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Button mButtonRequest = (Button) findViewById(R.id.requestBtn);
        mQueue = Volley.newRequestQueue(this);

        loadData();
        Toast.makeText(CartActivity.this,this.cart.toString(),Toast.LENGTH_SHORT).show();

        ListView itemCartListView = (ListView) findViewById(R.id.cart_ListView);
        HashMap<String,String>  itemQuanity = new HashMap<>();


        for(int i=0;i<cart.size();i++){
            itemQuanity.put(cart.get(i).getName(),"SID: "+cart.get(i).getSid()+"    Quantity: "+cart.get(i).getQuantity());
        }
        List<HashMap<String,String>> listItems = new ArrayList<>();

        SimpleAdapter adapter = new SimpleAdapter(this, listItems,R.layout.list_item_cart,
                                new String[]{"Name","Quantity"},
                                new int[]{R.id.text_ItemName,R.id.text_itemQuantity});

        Iterator it = itemQuanity.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String,String> resultMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultMap.put("Name",pair.getKey().toString());
            resultMap.put("Quantity",pair.getValue().toString());
            listItems.add(resultMap);
        }

        itemCartListView.setAdapter(adapter);

        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeRequest();
            }
        });

    }


    public void makeRequest(){

        final String url = "http://munro.humber.ca/~n01267335/CENG319/request.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(CartActivity.this,response,Toast.LENGTH_SHORT).show();
                        if(response.equals("Success")){
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("cart").commit();
                            finish();
                            startActivity(getIntent());
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                String sJson="";
                sJson="{\"uid\":\""+Prevalent.CurrentOnlineUser.getStudent_Number()+"\",\"items\":[";

                int count = 1;
                for(ItemHandler item: cart){
                        sJson+="{\"sid\":"+item.getSid()+",\"quantity\":"+item.getQuantity()+"},";
                }
                sJson = sJson.substring(0,sJson.length()-1)+"]}";
                Log.d("Test",sJson);
                params.put("request", sJson);

                return params;
            }
        };
        mQueue.add(postRequest);

    }


    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String json = sharedPreferences.getString("cart","");
        if (json.equals("")){
            this.cart = new ArrayList<ItemHandler>();
        }
        else{
            Gson gson = new Gson();
            Type itemCartType = new TypeToken<ArrayList<ItemHandler>>() {}.getType();
            this.cart = gson.fromJson(json,itemCartType);
        }

    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Type itemCartType = new TypeToken<ArrayList<ItemHandler>>() {}.getType();
        String json = gson.toJson(this.cart,itemCartType);
        editor.putString("cart",json);
        editor.commit();

    }
}
