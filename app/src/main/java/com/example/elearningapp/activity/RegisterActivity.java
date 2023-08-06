package com.example.elearningapp.activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningapp.R;


import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextUser, editTextCfPasword;
    Button buttonRegister;
    FirebaseAuth mAuth;
    TextView Login;
    ImageButton back;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.nhapEmail);
        editTextPassword = findViewById(R.id.password);
        editTextUser =findViewById(R.id.editTextName);
        editTextCfPasword = findViewById(R.id.cfpassword);

        buttonRegister = findViewById(R.id.btn_Register);

        Login = findViewById(R.id.loginNow);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                String email, password, user, cfpassword;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                cfpassword = String.valueOf(editTextCfPasword.getText());
                user = String.valueOf(editTextUser.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Hãy nhập email của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Hãy nhập mật khẩu của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(cfpassword)){
                    Toast.makeText(RegisterActivity.this, "Hãy nhập mật khẩu của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(user)){
                    Toast.makeText(RegisterActivity.this, "Hãy nhập tên của bạn", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải có ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(cfpassword)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu và xác nhận mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Tài khoản đã được tạo.",
                                            Toast.LENGTH_SHORT).show();
                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("name", user);
                                    userInfo.put("email", email);
                                    FirebaseFirestore.getInstance().collection("users")
                                            .document(mAuth.getUid()).
                                            set(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Log.v("Firebase", "AddOK");
                                                }
                                            });
                                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Đăng ký không thành công",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

    }
}