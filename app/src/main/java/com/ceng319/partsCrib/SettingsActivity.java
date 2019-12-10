package com.ceng319.partsCrib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ceng319.partsCrib.Prevalent.Prevalent;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import android.content.res.Configuration;

public class SettingsActivity extends AppCompatActivity {

    private Button confirm_btn;
    private Switch language_sw;
    private EditText currentPass, newPass, confirmNewPass;
    private CheckBox checkBox;
    private SlidrInterface slidr;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        currentPass = (EditText) findViewById(R.id.CurrentPassword);
        newPass = (EditText) findViewById(R.id.NewPassword);
        confirmNewPass = (EditText) findViewById(R.id.ConfirmNewPassword);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        language_sw = (Switch) findViewById(R.id.language_sw);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        slidr = Slidr.attach(this);
        LoadPreference();

        confirm_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validate();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                showPass(isChecked);
            }
        });

        language_sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language_sw.isChecked()) {
                    setAppLocale("fr");
                    saveSharedPreference();
                } else if (!language_sw.isChecked()){
                    setAppLocale("en");
                    saveSharedPreference();
                }
                finish();
                startActivity(getIntent());
            }
        });
//        Toast.makeText(this, Prevalent.CurrentOnlineUser.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        saveSharedPreference();
        super.onBackPressed();
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

    private void saveSharedPreference() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("language",language_sw.isChecked());
        editor.commit();
    }

    private void LoadPreference() {
        Boolean checked = sharedPref.getBoolean("language",false);
        language_sw.setChecked(checked);
    }

    private void validate() {
        String currentPassword = currentPass.getText().toString();
        String newPassword = newPass.getText().toString();
        String confirmNewPassword = confirmNewPass.getText().toString();

        if(TextUtils.isEmpty(currentPassword)){
            Toast.makeText(this, "Please Enter your current password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(newPassword)){
            Toast.makeText(this, "Please Enter a newPassword", Toast.LENGTH_SHORT).show();
        }

        else if(newPassword.compareTo (confirmNewPassword) == 0){
            //New Password added
        }
        else{
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
        }

        ChangePassword changePass = new ChangePassword();
        changePass.execute(Prevalent.CurrentOnlineUser.getEmail(),currentPassword,newPassword);
    }

    private void showPass(boolean isChecked) {
        if(isChecked) {
            currentPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            newPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            confirmNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            currentPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            confirmNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private class ChangePassword extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String email = args[0];
            String password = args[1];
            String passwordNew = args[2];
//            String url = "http://apollo.humber.ca/~n01156096/CENG319/accCon4.php";
            String url = "http://apollo.humber.ca/~n01267335/CENG319/account.php";

            JSONParser jsonParser = new JSONParser();
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("passwordNew", passwordNew));

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
            return json;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    if (result.getInt("success") == 1) {
                        Toast.makeText(SettingsActivity.this, result.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve data from server", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
