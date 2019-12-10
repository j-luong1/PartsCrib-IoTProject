package com.ceng319.partsCrib;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import org.json.JSONException;
import org.json.JSONObject;

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
    private EditText mQuantity;

    private Button mButtonAdd;
    private Button mButtonPlus;
    private Button mButtonMinus;

    private ArrayList<ItemHandler> cart;
    private ItemHandler newItem;
    private SlidrInterface slidr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();

        getSupportActionBar().setTitle(R.string.item_description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedItem = intent.getStringExtra("Item");

        mItemNameTV = (TextView) findViewById(R.id.Name_TV);
        mItemCategoryTV = (TextView) findViewById(R.id.Category_TV);
        mItemDescriptionTV = (TextView) findViewById(R.id.Description_TV);
        mItemQuantityTV = (TextView) findViewById(R.id.Quantity_TV);

        mButtonAdd = (Button) findViewById(R.id.addToCartBtn);
        mButtonPlus = (Button) findViewById(R.id.addBtn);
        mButtonMinus = (Button) findViewById(R.id.minusBtn);
        mQuantity = (EditText) findViewById(R.id.quantityField);

        mQueue = Volley.newRequestQueue(this);
        slidr = Slidr.attach(this);

        jsonParse();
        loadData();

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ItemHandler newItem = new ItemHandler(itemName,Integer.parseInt(selectedItem),Integer.parseInt(mQuantity.getText().toString()));
                createNewItem(newItem);
            }
        });

        mButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(mQuantity.getText().toString())+1;
                if(quantity<10) {
                    mQuantity.setText(Integer.toString(quantity));
                }
            }
        });

        mButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(mQuantity.getText().toString());
                if(quantity>1) {
                    quantity = quantity - 1;
                    mQuantity.setText(Integer.toString(quantity));
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void jsonParse(){

        String url = "http://apollo.humber.ca/~n01267335/CENG319/query.php?item="+selectedItem;

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

    public void createNewItem(final ItemHandler item){
        if (cart.isEmpty()) {
            this.cart.add(item);
            saveData();
            Toast.makeText(ItemActivity.this,R.string.add_yes,Toast.LENGTH_SHORT).show();
        } else {
            for (int i=0; i< cart.size(); i++) {
                if (cart.get(i).getSid() == item.getSid()) {

                    final int cartSpot = i;

                    AlertDialog.Builder confirm = new AlertDialog.Builder(ItemActivity.this);
                    confirm.setTitle(R.string.order_exists);
                    confirm.setMessage(R.string.add_prompt);
                    confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            int initQuantity = cart.get(cartSpot).getQuantity();
                            int addThis = item.getQuantity();
                            cart.get(cartSpot).setQuantity(initQuantity + addThis);
                            saveData();
                            Toast.makeText(ItemActivity.this,R.string.add_yes,Toast.LENGTH_SHORT).show();

                        }
                    });
                    confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(ItemActivity.this,R.string.cancel,Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = confirm.create();
                    dialog.show();

                    return;
                }
            }
                    this.cart.add(item);
                    saveData();
                    Toast.makeText(ItemActivity.this,R.string.add_yes,Toast.LENGTH_SHORT).show();
        }
    }
}
