package com.ceng319.partsCrib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputFName, InputLName, InputStudentNumber, InputPassword, InputConfirmPassword, InputEmail;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputFName = (EditText) findViewById(R.id.register_FName);
        InputLName = (EditText) findViewById(R.id.register_LName);
        InputStudentNumber = (EditText) findViewById(R.id.register_student_number_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputConfirmPassword = (EditText) findViewById(R.id.register_confirm_password_input);
        InputEmail = (EditText) findViewById(R.id.register_email_input);
        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String FName = InputFName.getText().toString();
        String LName = InputLName.getText().toString();
        String StudentNumber = InputStudentNumber.getText().toString();
        String Password = InputPassword.getText().toString();
        String ConfirmPassword = InputConfirmPassword.getText().toString();
        String Email = InputEmail.getText().toString();

        if(TextUtils.isEmpty(FName)){
            Toast.makeText(this, "Please Enter your first Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(LName)){
            Toast.makeText(this, "Please Enter your Last Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(StudentNumber)){
            Toast.makeText(this, R.string.enter_s_num, Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Password)){
            Toast.makeText(this, R.string.enter_pass, Toast.LENGTH_SHORT).show();
        }
       else if(TextUtils.isEmpty(Email)){
            Toast.makeText(this, "Please Enter a Email", Toast.LENGTH_SHORT).show();
        }
       else if(Password.compareTo (ConfirmPassword) == 0){
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateStudentNumber(FName, LName,  StudentNumber, Email, Password);
        }
       else{
            Toast.makeText(this,"Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

    private void ValidateStudentNumber(String FName, String LName, String StudentNumber, String Email, String Password) {
        RegisterAccount register = new RegisterAccount();
        register.execute(StudentNumber,Password,FName,LName,Email);
    }

    private class RegisterAccount extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String id = args[0];
            String password = args[1];
            String name = args[2];
            String last = args[3];
            String email = args[4];
            String url = "http://apollo.humber.ca/~n01267335/CENG319/account.php";

            JSONParser jsonParser = new JSONParser();
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username",id));
            params.add(new BasicNameValuePair("password",password));
            params.add(new BasicNameValuePair("name",name));
            params.add(new BasicNameValuePair("last",last));
            params.add(new BasicNameValuePair("email",email));


            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
            return json;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                if(result!=null) {
                    if(result.getInt("success")==1) {
                        loadingBar.dismiss();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this, result.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, result.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.db_error, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
