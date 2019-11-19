package com.ceng319.partsCrib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.ceng319.partsCrib.Model.Users;
import com.ceng319.partsCrib.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

public class LoginActivity extends AppCompatActivity {

    private EditText InputStudentNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";
    private CheckBox chkBoxRemeberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputStudentNumber = (EditText) findViewById(R.id.login_student_number_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);

        chkBoxRemeberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LoginUser();
            }

        });

    }
    private void LoginUser() {
        String studentNumber = InputStudentNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(studentNumber)){
            Toast.makeText(this, "Please Enter your Student Number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter your Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(studentNumber, password);

        }
    }

    private void AllowAccessToAccount(final String studentNumber, final String password) {
        if(chkBoxRemeberMe.isChecked()){
            Paper.book().write(Prevalent.UserStudentNumberKey, studentNumber);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if(dataSnapshot.child(parentDbName).child(studentNumber).exists()){
                  Users userData = dataSnapshot.child(parentDbName).child(studentNumber).getValue(Users.class);

                  if(userData.getStudent_Number().equals(studentNumber)){
                      if(userData.getPassword().equals(password)){

                          Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                          loadingBar.dismiss();

                          Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                          Prevalent.CurrentOnlineUser = userData;
                          startActivity(intent);
                      }
                  }

              }
              else{
                  Toast.makeText(LoginActivity.this, "User or Password wrong", Toast.LENGTH_SHORT).show();
                  loadingBar.dismiss();
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
