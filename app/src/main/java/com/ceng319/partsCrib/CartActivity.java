package com.ceng319.partsCrib;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

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

    private ListView mCartList;
    private SlidrInterface slidr;

    SimpleAdapter adapter;
    List<HashMap<String,String>> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setTitle(R.string.cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button mButtonRequest = (Button) findViewById(R.id.requestBtn);
        final Button mButtonRemove = (Button) findViewById(R.id.removeBtn);
        final ListView itemCartListView = (ListView) findViewById(R.id.cart_ListView);

        HashMap<String,String>  itemQuanity = new HashMap<>();
        mQueue = Volley.newRequestQueue(this);
        slidr = Slidr.attach(this);
        mCartList = (ListView) findViewById(R.id.cart_ListView);

        loadData();

        for(int i=0;i<cart.size();i++){
            itemQuanity.put(cart.get(i).getName(),"SID: "+cart.get(i).getSid()+"    Quantity: "+cart.get(i).getQuantity());
        }

        Iterator it = itemQuanity.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String,String> resultMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultMap.put("Name",pair.getKey().toString());
            resultMap.put("Quantity",pair.getValue().toString());
            listItems.add(resultMap);
        }

        adapter = new SimpleAdapter(this, listItems,R.layout.list_item_cart,
                                new String[]{"Name","Quantity"},
                                new int[]{R.id.text_ItemName,R.id.text_itemQuantity})
        {
            @Override
            public View getView (final int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Button removeBtn=(Button)v.findViewById(R.id.removeBtn);
                removeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder confirm = new AlertDialog.Builder(CartActivity.this);
                        confirm.setTitle(R.string.remove);
                        confirm.setMessage(R.string.are_you_sure);
                        confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cart.remove(position);
                                saveData();
                                listItems.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(CartActivity.this,R.string.cancel,Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog dialog = confirm.create();
                        dialog.show();
                    }
                });
                return v;
            }
        };

        itemCartListView.setAdapter(adapter);

        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeRequest();
            }
        });
    }

    //Altered in since I worked on it - Jonathan 11/25
    public void makeRequest(){

        final String url = "http://apollo.humber.ca/~n01267335/CENG319/request.php";

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
