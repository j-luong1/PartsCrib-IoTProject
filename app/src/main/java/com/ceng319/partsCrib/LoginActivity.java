package com.ceng319.partsCrib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ceng319.partsCrib.Model.Users;
import com.ceng319.partsCrib.Prevalent.Prevalent;
import com.rey.material.widget.CheckBox;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText InputStudentNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private CheckBox chkBoxRemeberMe;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputStudentNumber = (EditText) findViewById(R.id.login_student_number_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);

        chkBoxRemeberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.custom) == PackageManager.PERMISSION_GRANTED) {
                    LoginUser();
                } else { ActivityCompat.requestPermissions(LoginActivity.this, new String[] {Manifest.permission.custom}, REQUEST_CODE_ASK_PERMISSIONS); }
            }
        });
    }

    @Override
    protected void onStop() {
        loadingBar.dismiss();
        super.onStop();
    }

    public void LoginUser() {
        String studentNumber = InputStudentNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(studentNumber)){
            Toast.makeText(this, R.string.enter_s_num, Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, R.string.enter_pass, Toast.LENGTH_SHORT).show();
        }
        else{
            if(chkBoxRemeberMe.isChecked()){
                Paper.book().write(Prevalent.UserStudentNumberKey, studentNumber);
                Paper.book().write(Prevalent.UserPasswordKey, password);
            } else {
                Paper.book().destroy();
            }

            loadingBar.setTitle(R.string.logging);
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AttemptLogin attemptLogin = new AttemptLogin();
            attemptLogin.execute(studentNumber,password);

        }
    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

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
                    if(result.getInt("success")==1) {
                        //CurrentOnlineUser data is saved here, alter if necessary
                        Users userData = new Users(result.getString("id"),InputPassword.getText().toString(),
                                result.getString("name"),result.getString("uid"),result.getString("email"));
                        Prevalent.CurrentOnlineUser = userData;

                        //Function call to go to HomeActivity
                        success();
                    } else {
                        Toast.makeText(LoginActivity.this, result.getString("message"), Toast.LENGTH_SHORT).show();
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
