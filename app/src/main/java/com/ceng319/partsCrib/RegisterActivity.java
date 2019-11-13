package com.ceng319.partsCrib;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputFName, InputLName, InputStudentNumber, InputPassword, InputConfirmPassword, InputEmail;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
            Toast.makeText(this, "Please Enter your StudentNumber", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
        }
       else if(TextUtils.isEmpty(Email)){
            Toast.makeText(this, "Please Enter a Email", Toast.LENGTH_SHORT).show();
        }
       else if(Password.compareTo (ConfirmPassword) == 0){
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateStudentNumber(FName, LName,  StudentNumber, Email, Password);
        }
       else{
            Toast.makeText(this,"Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

    private void ValidateStudentNumber(final String FName, final String LName, final String studentNumber, final String email, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(studentNumber).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Student_Number", studentNumber);
                    userdataMap.put("First_Name", FName);
                    userdataMap.put("Last_Name", LName);
                    userdataMap.put("Email", email);
                    userdataMap.put("Password", password);

                    RootRef.child("Users").child(studentNumber).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Network Error: Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "This " + studentNumber + " already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}
