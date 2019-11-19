package com.ceng319.partsCrib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ceng319.partsCrib.Prevalent.Prevalent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity{
    public static final String SHARED_PREFS = Prevalent.CurrentOnlineUser.getStudent_Number();;
    private RequestQueue mQueue;
    private String selectedItem;
    private String itemCategory;
    private String itemName;
    private String itemDescription;
    private String itemQuantity;

    private TextView mItemNameTV;
    private TextView mItemCategoryTV;
    private TextView mItemDescriptionTV;
    private TextView mItemQuantityTV;

    private Button mButtonAdd;
    private EditText mQuantity;

    private ArrayList<ItemHandler> cart;
    private ItemHandler newItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        selectedItem = intent.getStringExtra("Item");

        mItemNameTV = (TextView) findViewById(R.id.Name_TV);
        mItemCategoryTV = (TextView) findViewById(R.id.Category_TV);
        mItemDescriptionTV = (TextView) findViewById(R.id.Description_TV);
        mItemQuantityTV = (TextView) findViewById(R.id.Quantity_TV);

        mButtonAdd = (Button) findViewById(R.id.addToCartBtn);
        mQuantity = (EditText) findViewById(R.id.quantityField);

        mQueue = Volley.newRequestQueue(this);

        jsonParse();
        loadData();

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//
                ItemHandler newItem = new ItemHandler(itemName,Integer.parseInt(selectedItem),Integer.parseInt(mQuantity.getText().toString()));
                createNewItem(newItem);
            }
        });
    }

    private void jsonParse(){

        String url = "http://munro.humber.ca/~n01267335/CENG319/query.php?item="+selectedItem;
//        Toast.makeText(ItemActivity.this,url,Toast.LENGTH_SHORT).show();

        JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            itemCategory = response.getString("Category");
                            itemName = response.getString("Name");
                            itemDescription = response.getString("Description");
                            itemQuantity = response.getString("Quantity");

                            updateTextView();
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

    private void updateTextView(){
        mItemNameTV.setText("Name: "+itemName);
        mItemCategoryTV.setText("Category: "+itemCategory);
        mItemDescriptionTV.setText("Description: "+itemDescription);
        mItemQuantityTV.setText("Quantity: "+itemQuantity);
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

    public void createNewItem(ItemHandler item){
        this.cart.add(item);
        saveData();
        Toast.makeText(ItemActivity.this,"Added to Cart",Toast.LENGTH_SHORT).show();
    }
}
