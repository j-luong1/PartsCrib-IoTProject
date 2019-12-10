package com.ceng319.partsCrib;

import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ceng319.partsCrib.Model.Users;
import com.ceng319.partsCrib.Prevalent.Prevalent;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Locale;

import static com.ceng319.partsCrib.Prevalent.Prevalent.UserPasswordKey;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton, loginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProcessPreference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        joinNowButton = (Button) findViewById(R.id.main_join_now_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        String UserStudentNumberKey  = Paper.book().read(Prevalent.UserStudentNumberKey);
        String UserPasswordKey  = Paper.book().read(Prevalent.UserPasswordKey);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }

        });

        if(UserStudentNumberKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserStudentNumberKey) && !TextUtils.isEmpty(UserPasswordKey)){
                loadingBar.setTitle(R.string.already_logged);
                loadingBar.setMessage("Please Wait");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                AttemptResignin attemptLogin = new AttemptResignin();
                attemptLogin.execute(UserStudentNumberKey,UserPasswordKey);
            }
        }
    }

    protected void onStop() {
        loadingBar.dismiss();
        super.onStop();
    }

    private void ProcessPreference() {
        SharedPreferences settings = getSharedPreferences("SettingsActivity",MODE_PRIVATE);
        Boolean language = settings.getBoolean("language",false);

        if (language) {
            setAppLocale("fr");
        } else {
            setAppLocale("en");
        }
    }

    private void setAppLocale(String localeCode) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config,dm);
    }

    private class AttemptResignin extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String name = args[0];
            String password = args[1];
            String url = "http://apollo.humber.ca/~n01267335/CENG319/account.php";
            JSONParser jsonParser = new JSONParser();

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username",name));
            params.add(new BasicNameValuePair("password",password));

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
            return json;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                if(result!=null) {
                    //Pass string to HomeActivity page
                    if(result.getInt("success")==1) {
                        Users userData = new Users(result.getString("id"),UserPasswordKey,
                                result.getString("name"),result.getString("uid"),result.getString("email"));
                        Prevalent.CurrentOnlineUser = userData;
                        success();
                    } else {
                        Toast.makeText(MainActivity.this,result.getString("message"), Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),R.string.db_error, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void success() {
        Intent intent = new Intent (this, HomeActivity.class);
        startActivity(intent);
    }


}
